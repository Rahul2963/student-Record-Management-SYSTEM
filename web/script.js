document.addEventListener('DOMContentLoaded', () => {
    // Load students on page load
    loadStudents();

    // Handle form submission
    document.getElementById('studentForm').addEventListener('submit', async (e) => {
        e.preventDefault();

        const student = {
            name: document.getElementById('name').value,
            email: document.getElementById('email').value,
            department: document.getElementById('department').value,
            grade: parseFloat(document.getElementById('grade').value)
        };

        try {
            const response = await fetch('http://localhost:8000/add-student', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(student)
            });

            if (response.ok) {
                alert('Student added successfully!');
                loadStudents();
                document.getElementById('studentForm').reset();
            } else {
                alert('Failed to add student.');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    });
});

async function loadStudents() {
    try {
        const response = await fetch('http://localhost:8000/get-students');
        const students = await response.json();

        const tableBody = document.getElementById('studentTableBody');
        tableBody.innerHTML = students.map(student =>
            `<tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.email}</td>
                <td>${student.department}</td>
                <td>${student.grade}</td>
            </tr>`
        ).join('');s
    } catch (error) {
        console.error('Error loading students:', error);

    }
}