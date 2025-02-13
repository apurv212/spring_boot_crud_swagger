const backendUrl = "http://localhost:9090/books";

async function addBook() {
    let title = document.getElementById("title").value;
    let author = document.getElementById("author").value;

    if (!title || !author) {
        alert("Please enter both title and author!");
        return;
    }

    try {
        let response = await fetch(`${backendUrl}/addbook`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ title, author })
        });

        if (response.ok) {
            alert("Book added successfully!");
            document.getElementById("title").value = "";
            document.getElementById("author").value = "";
            fetchBooks();  // Auto-refresh book list
        } else {
            alert("Failed to add book.");
        }
    } catch (error) {
        console.error("Error adding book:", error);
        alert("Error adding book.");
    }
}

async function fetchBooks() {
    try {
        let response = await fetch(`${backendUrl}/getallbooks`);
        let data = await response.json();

        const tableBody = document.querySelector("#bookTable tbody");
        tableBody.innerHTML = "";

        data.forEach(book => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${book.id}</td>
                <td><input type="text" id="title-${book.id}" value="${book.title}"></td>
                <td><input type="text" id="author-${book.id}" value="${book.author}"></td>
                <td><button onclick="updateBook(${book.id})" class="update-btn">Update</button></td>
                <td><button onclick="deleteBook(${book.id})" class="delete-btn">Delete</button></td>
            `;
            tableBody.appendChild(row);
        });

    } catch (error) {
        console.error("Error fetching books:", error);
        alert("Error fetching books.");
    }
}

async function updateBook(bookId) {
    let updatedTitle = document.getElementById(`title-${bookId}`).value;
    let updatedAuthor = document.getElementById(`author-${bookId}`).value;

    if (!updatedTitle || !updatedAuthor) {
        alert("Please enter both title and author!");
        return;
    }

    try {
        let response = await fetch(`${backendUrl}/updatebook/${bookId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ title: updatedTitle, author: updatedAuthor })
        });

        if (response.ok) {
            alert("Book updated successfully!");
            fetchBooks();
        } else {
            alert("Failed to update book.");
        }
    } catch (error) {
        console.error("Error updating book:", error);
        alert("Error updating book.");
    }
}

async function deleteBook(bookId) {
    if (!confirm("Are you sure you want to delete this book?")) return;

    try {
        let response = await fetch(`${backendUrl}/deletebookbyid/${bookId}`, {
            method: "DELETE"
        });

        if (response.ok) {
            alert("Book deleted successfully!");
            fetchBooks();
        } else {
            alert("Failed to delete book.");
        }
    } catch (error) {
        console.error("Error deleting book:", error);
        alert("Error deleting book.");
    }
}

document.addEventListener("DOMContentLoaded", fetchBooks);
