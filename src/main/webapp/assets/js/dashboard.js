document.addEventListener('DOMContentLoaded', () => {
    console.log("EventHive Dashboard JS Loaded");

    // Add confirmation to all forms with the 'requires-confirm' class
    const confirmationForms = document.querySelectorAll('form.requires-confirm');

    confirmationForms.forEach(form => {
        form.addEventListener('submit', function (event) {
            // Get message from data-confirm attribute or use a default
            const message = this.dataset.confirm || 'Are you sure you want to perform this action?';

            // If the user cancels, prevent the form from submitting
            if (!confirm(message)) {
                event.preventDefault();
            }
        });
    });
});