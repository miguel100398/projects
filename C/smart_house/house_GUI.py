from queue import Queue
from threading import Thread
from time import sleep
from tkinter import *

from serial import Serial

from tkinter import Tk, Label, Button, messagebox
from tkinter.ttk import Style


work = True


def f(s):
    return s + " C"

class SerialInterface(Serial):
    def __init__(self, port, gui_values):
        super().__init__(port, 9600)
        if self.isOpen():
            self.close()
        self.open()
        self.jobs = Queue()
        self.gui_values = gui_values
        self.worker_serial = Thread(target=self.worker, daemon=True)
        self.worker_serial.start()

    def update_gui(self):
        data = self.read_until().decode()
        if "-S-" in data:
            self.gui_values.put(data)

    def get_response(self):
        while True:
            response = self.read_until().decode()
            if "-I-" in response:
                break
            elif "-E-" in response:
                messagebox.showerror(title="Write Operation Error", message=response)
                break

    def work(self, job):
        for operation in job:
            self.write(operation.encode())
        if len(job) > 1:
            self.get_response()

    def worker(self):
        # send any random sequence to start the reading
        self.write(b'#')
        while True:
            if self.jobs.empty():
                self.update_gui()
            else:
                job = self.jobs.get()
                self.work(job)

class SerialMonitor(SerialInterface):
    def __init__(self, port):
        self.max_temp = StringVar()
        self.min_temp = StringVar()
        self.curr_temp = StringVar()
        self.lower_threshold = StringVar()
        self.upper_threshold = StringVar()
        self.led_intensity = StringVar()

        self.led_intensity.set("0%")
        self.max_temp.set("0 C")
        self.min_temp.set("0 C")
        self.curr_temp.set("0 C")

        self.lower_threshold.set("off")
        self.upper_threshold.set("off")

        self.gui_values = Queue()
        SerialInterface.__init__(self, port, self.gui_values)

        self.worker_gui = Thread(target=self.gui_updater, daemon=True)
        self.worker_gui.start()


    def set_lower_threshold(self, value):
        self.jobs.put(['m', str(value), '\r\n'])

    def set_upper_threshold(self, value):
        self.jobs.put(['M', str(value), '\r\n'])

    def set_led_intensity(self, value):
        self.jobs.put(['i', str(value), '\r\n'])

    def increase_lower_threshold(self):
        self.jobs.put('B')

    def decrease_lower_threshold(self):
        self.jobs.put('b')

    def increase_upper_treshold(self):
        self.jobs.put('N')

    def decrease_upper_threshold(self):
        self.jobs.put('n')

    def increase_led_intensity(self):
        self.jobs.put('L')

    def decrease_led_intensity(self):
        self.jobs.put('l')

    def gui_updater(self):
        while True:
            raw_data = self.gui_values.get()
            if "-S-" not in raw_data:
                continue
            raw_data = raw_data.split()
            self.curr_temp.set(f(raw_data[2]))
            self.max_temp.set(f(raw_data[5]))
            self.min_temp.set(f(raw_data[8]))
            self.lower_threshold.set(raw_data[11].replace(',', ''))
            self.upper_threshold.set(raw_data[13].replace(',', ''))
            self.led_intensity.set(raw_data[15].replace(',', ''))

class HouseGUI(Frame, SerialMonitor):
    def __init__(self):
        self.working = True
        SerialMonitor.__init__(self, "COM5")
        Frame.__init__(self, bg="#d3d3d3")
        self.buildUI()

    def close_gui(self):
        self.quit()
        self.close()
        exit()

    def buildUI(self):
        self.master.title("House Control System")
        self.style = Style()
        self.style.theme_use("default")


        ### Left frame
        Label(self, text="House Control System", font='Helvetica 14 bold', bg="#d3d3d3").pack(side=TOP, fill=X, pady=5)
        frame = Frame(self)
        frame.pack(fill=BOTH, expand=True)
        frame_left = Frame(frame, relief=RAISED, borderwidth=1, padx=8)
        frame_left.pack(fill=BOTH, side=LEFT, expand=True)

        Label(frame_left, text="Temperatures", font='Helvetica 12 bold', borderwidth=1).pack(side=TOP, fill=BOTH, pady=5)
        frame_temps = Frame(frame_left)

        ## minimum temperature labels
        frame_min = Frame(frame_temps)
        frame_min.pack(side=LEFT)
        Label(frame_min, text="Minimum").pack(side=BOTTOM)
        self.min_temp_entry = Label(frame_min, text="0",font='Helvetica 15 bold', fg="blue", textvariable=self.min_temp).pack(side=TOP)

        ## current temperature labels
        frame_curr = Frame(frame_temps)
        frame_curr.pack(side=LEFT)
        Label(frame_curr, text="Current").pack(side=BOTTOM)
        self.curr_temp_field = Label(frame_curr, text="0", font='Helvetica 20 bold', fg="green", textvariable=self.curr_temp).pack(side=TOP, padx=8)

        ## maximum temperatures
        frame_temps.pack(side=TOP)
        frame_max = Frame(frame_temps)
        frame_max.pack(side=LEFT)
        Label(frame_max, text="Maximum").pack(side=BOTTOM)
        self.max_temp_entry = Label(frame_max, text="0", font='Helvetica 15 bold', fg="red", textvariable=self.max_temp).pack(side=TOP)

        ## Thresholds
        temp_frame = Frame(frame_left)
        temp_frame.pack(side=TOP, fill=BOTH, pady=12)
        upper_frame = Frame(temp_frame)
        upper_frame.pack(side=TOP, fill=X)
        Label(upper_frame, text="Upper temperature threshold").pack(side=LEFT, fill=BOTH)
        self.up_treshold_label = Label(upper_frame, text="off", textvariable=self.upper_threshold).pack(side=RIGHT, fill=X)
        lower_frame = Frame(temp_frame)
        lower_frame.pack(side=TOP, fill=X)
        Label(lower_frame, text="Lower temperature threshold").pack(side=LEFT, fill=BOTH)
        self.low_threshold_label = Label(lower_frame, text="off", textvariable=self.lower_threshold).pack(side=RIGHT, fill=X)

        # buttom threshold controls
        bottom_frame = Frame(frame_left)
        bottom_frame.pack(side=TOP, fill=BOTH)
        left_bottom = Frame(bottom_frame)
        left_bottom.pack(side=LEFT, fill=BOTH, expand=True, padx=5)
        Label(left_bottom, text="Lower Threshold", font='Helvetica 10 bold').pack(side=TOP, fill=BOTH, pady=5)
        Button(left_bottom, text="Down", command=self.decrease_lower_threshold).pack(side=LEFT, fill=BOTH, expand=True)
        Button(left_bottom, text="Up", command=self.increase_lower_threshold).pack(side=RIGHT, fill=BOTH, expand=True)
        Label(left_bottom, text="Temperature:", font='Helvetica 10 bold').pack(side=TOP, fill=X, pady=5, padx=5)
        self.lower_threshold_entry = Entry(left_bottom, text="lower threshold")
        self.lower_threshold_entry.pack(side=TOP)
        Button(left_bottom, text='set', command=self.call_set_lower_threshold).pack(side=TOP, fill=BOTH, expand=1, padx=5)


        right_bottom = Frame(bottom_frame)
        right_bottom.pack(side=RIGHT, fill=BOTH, expand=True, padx=5)
        Label(right_bottom, text="Upper Threshold", font='Helvetica 10 bold').pack(side=TOP, fill=BOTH, pady=5)
        Button(right_bottom, text="Down", command=self.decrease_upper_threshold).pack(side=LEFT, fill=BOTH, expand=True)
        Button(right_bottom, text="Up", command=self.increase_upper_treshold).pack(side=RIGHT, fill=BOTH, expand=True)
        Label(right_bottom, text="Temperature:", font='Helvetica 10 bold').pack(side=TOP, fill=X, padx=5, pady=5)
        self.upper_threshold_entry = Entry(right_bottom, text="upper threshold")
        self.upper_threshold_entry.pack(side=TOP)
        Button(right_bottom, text='set', command=self.call_set_upper_threshold).pack(side=TOP, fill=BOTH, expand=1, padx=5)


        ## Led frame
        led_frame = Frame(frame_left)
        Label(frame_left, text="Led Light Intensity", font='Helvetica 12 bold').pack(side=TOP, fill=X, pady=10)
        led_frame.pack(fill=BOTH, side=TOP, expand=True)
        Label(led_frame, textvariable=self.led_intensity, font="Helvetica 18 bold").pack(side=LEFT, fill=BOTH, padx=(80, 40))
        led_buttons_frame = Frame(led_frame)
        led_buttons_frame.pack(side=LEFT)
        Button(led_buttons_frame, text='Up', command=self.increase_led_intensity).pack(side=LEFT, fill=BOTH, expand=1)
        Button(led_buttons_frame, text='Down', command=self.decrease_led_intensity).pack(side=RIGHT, fill=BOTH, expand=1)
        led_entry_frame = Frame(led_frame)
        led_entry_frame.pack(side=LEFT, pady=(0, 10))
        Label(led_entry_frame, text="Intensity", font='Helvetica 12 bold').pack(side=LEFT, padx=(15, 0))
        self.led_intensity_entry = Entry(led_entry_frame, text="Intensity")
        self.led_intensity_entry.pack(side=LEFT, expand=1, padx=5)
        Button(led_entry_frame, text='set', command=self.call_set_led_intensity).pack(side=RIGHT, fill=BOTH)


        # Bottom frame
        self.pack(fill=BOTH, expand=True)
        closeButton = Button(self, text="Close", command=self.close_gui)
        closeButton.pack(side=RIGHT, padx=5, pady=5)

    def call_set_led_intensity(self):
        self.set_led_intensity(self.led_intensity_entry.get())

    def call_set_lower_threshold(self):
        self.set_lower_threshold(self.lower_threshold_entry.get())

    def call_set_upper_threshold(self):
        self.set_upper_threshold(self.upper_threshold_entry.get())

def main():
    root = Tk()
    root.geometry("500x430")
    app = HouseGUI()
    root.mainloop()

if __name__ == '__main__':
    main()

