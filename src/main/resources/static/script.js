// Function to fetch presenter options based on the selected publisher
function fetchPresentersByPublisher(publisherId) {
  fetch(`/api/presenters?publisherId=${publisherId}`)
    .then((response) => response.json())
    .then((data) => {
      // Update the presenter options
      const presenterSelect = document.getElementById('presenter');
      presenterSelect.innerHTML = '';

      // Create and append option elements
      let firstPresenterId = null; // Variable to store the first presenter ID

      for (const presenter of data) {
        const option = document.createElement('option');
        option.value = presenter.id;
        option.text = presenter.name;
        presenterSelect.appendChild(option);

        if (firstPresenterId === null) {
          firstPresenterId = presenter.id; // Store the first presenter ID
        }
      }

      // Select the first presenter if available
      if (firstPresenterId) {
        presenterSelect.value = firstPresenterId;
      }
    })
    .catch((error) => console.error(error));
}

// Rest of the code remains the same

// Event listener for the publisher selection
const publisherSelect = document.getElementById('publisher');
publisherSelect.addEventListener('change', function () {
  const selectedPublisherId = this.value;
  // Fetch presenter options based on the selected publisher
  fetchPresentersByPublisher(selectedPublisherId);
});

// Trigger change event on page load to fetch initial presenter options
publisherSelect.dispatchEvent(new Event('change'));

// Validate presenter to be required
const presenterSelect = document.getElementById('presenter');
const createEventForm = document.getElementById('createEventForm');
createEventForm.addEventListener('submit', function (event) {
  if (presenterSelect.value === '') {
    event.preventDefault();
    alert('Presenter is required. Please select a presenter.');
  }
});
