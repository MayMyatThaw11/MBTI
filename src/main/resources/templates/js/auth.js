// Handle form submissions and display messages
document.addEventListener('DOMContentLoaded', function() {
  // Check for flash messages in modals
  const urlParams = new URLSearchParams(window.location.search);
  const error = urlParams.get('error');
  const success = urlParams.get('success');
  
  if (error) {
    // Show login modal if there's an error
    const loginModal = document.getElementById('loginModal');
    if (loginModal) {
      loginModal.classList.remove('hidden');
    }
  }
  
  // Add event listeners for form submissions
  const loginForm = document.querySelector('form[action="/auth/login"]');
  const registerForm = document.querySelector('form[action="/auth/register"]');
  
  if (loginForm) {
    loginForm.addEventListener('submit', function(e) {
      // Basic client-side validation
      const email = this.querySelector('input[name="email"]');
      const password = this.querySelector('input[name="passwordHash"]');
      
      if (!email.value || !password.value) {
        e.preventDefault();
        alert('Please fill in all fields');
      }
    });
  }
  
  if (registerForm) {
    registerForm.addEventListener('submit', function(e) {
      // Basic client-side validation
      const username = this.querySelector('input[name="username"]');
      const email = this.querySelector('input[name="email"]');
      const password = this.querySelector('input[name="passwordHash"]');
      
      if (!username.value || !email.value || !password.value) {
        e.preventDefault();
        alert('Please fill in all fields');
        return;
      }
      
      // Validate email format
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(email.value)) {
        e.preventDefault();
        alert('Please enter a valid email address');
        return;
      }
      
      // Validate password length
      if (password.value.length < 6) {
        e.preventDefault();
        alert('Password should be at least 6 characters long');
      }
    });
  }
});