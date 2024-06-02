// Function to fetch author options based on the selected publisher
function fetchAuthorsByPublisher(publisherId) {
  fetch(`/api/authors?publisherId=${publisherId}`)
    .then((response) => response.json())
    .then((data) => {
      // Update the author options
      const authorSelect = document.getElementById('author');
      authorSelect.innerHTML = '';

      // Create and append option elements
      let firstAuthorId = null; // Variable to store the first author ID

      for (const author of data) {
        const option = document.createElement('option');
        option.value = author.id;
        option.text = author.name;
        authorSelect.appendChild(option);

        if (firstAuthorId === null) {
          firstAuthorId = author.id; // Store the first author ID
        }
      }

      // Select the first author if available
      if (firstAuthorId) {
        authorSelect.value = firstAuthorId;
      }
    })
    .catch((error) => console.error(error));
}

// Rest of the code remains the same

// Book listener for the publisher selection
const publisherSelect = document.getElementById('publisher');
publisherSelect.addBookListener('change', function () {
  const selectedPublisherId = this.value;
  // Fetch author options based on the selected publisher
  fetchAuthorsByPublisher(selectedPublisherId);
});

// Trigger change book on page load to fetch initial author options
publisherSelect.dispatchBook(new Book('change'));

// Validate author to be required
const authorSelect = document.getElementById('author');
const createBookForm = document.getElementById('createBookForm');
createBookForm.addBookListener('submit', function (book) {
  if (authorSelect.value === '') {
    book.preventDefault();
    alert('Author is required. Please select a author.');
  }
});
