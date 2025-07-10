from dataclasses import dataclass, field
from typing import List
import tkinter as tk
from tkinter import messagebox

@dataclass
class UserProfile:
    """Basic information about the user for tracking."""
    weight_kg: float
    height_cm: float
    age: int
    hbp_history: bool = False
    conditions: List[str] = field(default_factory=list)

    def bmi(self) -> float:
        """Calculate Body Mass Index."""
        height_m = self.height_cm / 100.0
        return self.weight_kg / (height_m ** 2)

@dataclass
class FitnessTracker:
    """Simple tracker for steps, distance, calories and blood pressure."""
    user: UserProfile
    step_length_m: float = 0.762  # average step length in meters
    kcal_per_step: float = 0.04   # crude estimate of kcal burned per step
    steps: int = 0

    def add_steps(self, count: int) -> None:
        """Add steps to the tracker."""
        if count < 0:
            raise ValueError("Step count cannot be negative")
        self.steps += count

    @property
    def kilometers(self) -> float:
        return (self.steps * self.step_length_m) / 1000.0

    @property
    def calories(self) -> float:
        return self.steps * self.kcal_per_step

    def estimate_blood_pressure(self) -> str:
        """Very rough estimation of blood pressure based on user profile."""
        systolic = 120
        diastolic = 80

        # Age effect: mild increase after 40
        if self.user.age > 40:
            systolic += 0.5 * (self.user.age - 40)
            diastolic += 0.3 * (self.user.age - 40)

        # BMI effect
        bmi = self.user.bmi()
        if bmi > 25:
            systolic += 10
            diastolic += 5
        if bmi > 30:
            systolic += 5
            diastolic += 3

        # HBP history or medical conditions
        if self.user.hbp_history or any(c.lower() in {"hypertension", "heart"} for c in self.user.conditions):
            systolic += 15
            diastolic += 10

        return f"{int(systolic)}/{int(diastolic)} mmHg"


class FitnessAppUI:
    """Tkinter based front end for the fitness tracker."""

    def __init__(self, master: tk.Tk) -> None:
        self.master = master
        master.title("Fitness Tracker")

        self.weight_var = tk.StringVar(value="70")
        self.height_var = tk.StringVar(value="175")
        self.age_var = tk.StringVar(value="30")
        self.hbp_var = tk.BooleanVar()
        self.conditions_var = tk.StringVar()
        self.step_entry_var = tk.StringVar()

        row = 0
        tk.Label(master, text="Weight (kg)").grid(row=row, column=0, sticky="e")
        tk.Entry(master, textvariable=self.weight_var).grid(row=row, column=1)

        row += 1
        tk.Label(master, text="Height (cm)").grid(row=row, column=0, sticky="e")
        tk.Entry(master, textvariable=self.height_var).grid(row=row, column=1)

        row += 1
        tk.Label(master, text="Age").grid(row=row, column=0, sticky="e")
        tk.Entry(master, textvariable=self.age_var).grid(row=row, column=1)

        row += 1
        tk.Checkbutton(master, text="HBP History", variable=self.hbp_var).grid(row=row, columnspan=2, sticky="w")

        row += 1
        tk.Label(master, text="Conditions (comma separated)").grid(row=row, columnspan=2, sticky="w")
        row += 1
        tk.Entry(master, textvariable=self.conditions_var).grid(row=row, columnspan=2, sticky="we")

        row += 1
        tk.Label(master, text="Steps to add").grid(row=row, column=0, sticky="e")
        tk.Entry(master, textvariable=self.step_entry_var).grid(row=row, column=1)

        row += 1
        tk.Button(master, text="Add Steps", command=self.add_steps).grid(row=row, columnspan=2, pady=4)

        row += 1
        self.stats_label = tk.Label(master, text="")
        self.stats_label.grid(row=row, columnspan=2, pady=4)

        self.tracker = None

    def _get_profile(self) -> UserProfile:
        return UserProfile(
            weight_kg=float(self.weight_var.get()),
            height_cm=float(self.height_var.get()),
            age=int(self.age_var.get()),
            hbp_history=self.hbp_var.get(),
            conditions=[c.strip() for c in self.conditions_var.get().split(',') if c.strip()],
        )

    def add_steps(self) -> None:
        try:
            count = int(self.step_entry_var.get())
        except ValueError:
            messagebox.showerror("Input Error", "Invalid step count")
            return

        if not self.tracker:
            try:
                profile = self._get_profile()
            except ValueError as exc:
                messagebox.showerror("Input Error", str(exc))
                return
            self.tracker = FitnessTracker(user=profile)

        try:
            self.tracker.add_steps(count)
        except ValueError as exc:
            messagebox.showerror("Input Error", str(exc))
            return

        self._update_stats()

    def _update_stats(self) -> None:
        if not self.tracker:
            return
        stats = (
            f"Steps: {self.tracker.steps}\n"
            f"Distance: {self.tracker.kilometers:.2f} km\n"
            f"Calories: {self.tracker.calories:.1f} kcal\n"
            f"Estimated BP: {self.tracker.estimate_blood_pressure()}"
        )
        self.stats_label.config(text=stats)


def run_gui() -> None:
    root = tk.Tk()
    app = FitnessAppUI(root)
    root.mainloop()


def demo() -> None:
    """Demo usage of FitnessTracker."""
    profile = UserProfile(weight_kg=70, height_cm=175, age=30)
    tracker = FitnessTracker(user=profile)
    tracker.add_steps(5000)

    print(f"Steps: {tracker.steps}")
    print(f"Distance: {tracker.kilometers:.2f} km")
    print(f"Calories: {tracker.calories:.1f} kcal")
    print(f"Estimated blood pressure: {tracker.estimate_blood_pressure()}")


if __name__ == "__main__":
    run_gui()
