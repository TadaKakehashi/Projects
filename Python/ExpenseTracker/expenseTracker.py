import tkinter as tk
from tkinter import ttk, messagebox
import csv
from datetime import datetime
import os

FILENAME = 'expenses.csv'

class ExpenseTracker:
    def __init__(self, root):
        self.root = root
        self.root.title("Expense Tracker")
        self.root.geometry("500x500")
        self.create_widgets()
        self.load_expenses()

    def create_widgets(self):
        ttk.Label(self.root, text="Date (YYYY-MM-DD):").pack(pady=5)
        self.date_entry = ttk.Entry(self.root)
        self.date_entry.pack(pady=5)
        self.date_entry.insert(0, datetime.now().strftime('%Y-%m-%d'))

        ttk.Label(self.root, text="Category: (Food, Clothes ,Shoes ,etc)").pack(pady=8)
        self.category_entry = ttk.Entry(self.root)
        self.category_entry.pack(pady=5)

        ttk.Label(self.root, text="Amount:").pack(pady=5)
        self.amount_entry = ttk.Entry(self.root)
        self.amount_entry.pack(pady=5)

        ttk.Button(self.root, text="Add Expense", command=self.add_expense).pack(pady=10)

        ttk.Button(self.root, text="View All Expenses", command=self.view_expenses).pack(pady=5)
        ttk.Button(self.root, text="View Summary", command=self.view_summary).pack(pady=5)

        self.output_area = tk.Text(self.root, height=15, width=60)
        self.output_area.pack(pady=10)

    def add_expense(self):
        date = self.date_entry.get()
        category = self.category_entry.get()
        amount = self.amount_entry.get()

        if not (date and category and amount):
            messagebox.showwarning("Missing Data", "Please fill all fields.")
            return

        try:
            float(amount) 
        except ValueError:
            messagebox.showerror("Invalid Amount", "Amount must be a number.")
            return

        with open(FILENAME, 'a', newline='') as file:
            writer = csv.writer(file)
            writer.writerow([date, category, amount])

        messagebox.showinfo("Success", "Expense added successfully!")
        self.clear_entries()
        self.load_expenses()

    def view_expenses(self):
        self.output_area.delete(1.0, tk.END)
        if not os.path.exists(FILENAME):
            self.output_area.insert(tk.END, "No expenses recorded yet.")
            return

        with open(FILENAME, 'r') as file:
            reader = csv.reader(file)
            self.output_area.insert(tk.END, f"{'Date':<15}{'Category':<20}{'Amount':<10}\n")
            self.output_area.insert(tk.END, "-" * 45 + "\n")
            for row in reader:
                self.output_area.insert(tk.END, f"{row[0]:<15}{row[1]:<20}{row[2]:<10}\n")

    def view_summary(self):
        self.output_area.delete(1.0, tk.END)
        if not os.path.exists(FILENAME):
            self.output_area.insert(tk.END, "No expenses to summarize.")
            return

        summary = {}
        total = 0.0
        with open(FILENAME, 'r') as file:
            reader = csv.reader(file)
            for row in reader:
                category = row[1]
                amount = float(row[2])
                total += amount
                if category in summary:
                    summary[category] += amount
                else:
                    summary[category] = amount

        self.output_area.insert(tk.END, f"Total Spent: Rs. {total:.2f}\n\n")
        self.output_area.insert(tk.END, "Category-wise Breakdown:\n")
        self.output_area.insert(tk.END, "-" * 30 + "\n")
        for cat, amt in summary.items():
            self.output_area.insert(tk.END, f"{cat:<20}Rs. {amt:.2f}\n")

    def clear_entries(self):
        self.category_entry.delete(0, tk.END)
        self.amount_entry.delete(0, tk.END)

    def load_expenses(self):
        if not os.path.exists(FILENAME):
            with open(FILENAME, 'w', newline='') as file:
                pass  

if __name__ == '__main__':
    root = tk.Tk()
    app = ExpenseTracker(root)
    root.mainloop()
