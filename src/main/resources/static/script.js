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

// Event listener for the publisher selection
const publisherSelect = document.getElementById('publisher');
publisherSelect.addEventListener('change', function () {
  const selectedPublisherId = this.value;
  // Fetch author options based on the selected publisher
  fetchAuthorsByPublisher(selectedPublisherId);
});

// Trigger change event on page load to fetch initial author options
publisherSelect.dispatchEvent(new Event('change'));

// Validate author to be required
const authorSelect = document.getElementById('author');
const createEventForm = document.getElementById('createEventForm');
createEventForm.addEventListener('submit', function (event) {
  if (authorSelect.value === '') {
    event.preventDefault();
    alert('Author is required. Please select a author.');
  }
});
