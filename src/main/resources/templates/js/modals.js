// Modal logic
document.addEventListener('DOMContentLoaded', function() {
  const loginModal = document.getElementById('loginModal');
  const registerModal = document.getElementById('registerModal');

  const openLogin = document.getElementById('openLogin');
  const openLoginMobile = document.getElementById('openLoginMobile');
  const openRegister = document.getElementById('openRegister');
  const switchToLogin = document.getElementById('switchToLogin');

  const closeLogin = document.getElementById('closeLogin');
  const closeRegister = document.getElementById('closeRegister');

  // Open login modal
  if (openLogin) {
    openLogin.addEventListener('click', () => loginModal.classList.remove('hidden'));
  }
  
  if (openLoginMobile) {
    openLoginMobile.addEventListener('click', () => loginModal.classList.remove('hidden'));
  }

  // Close buttons
  if (closeLogin) {
    closeLogin.addEventListener('click', () => loginModal.classList.add('hidden'));
  }
  
  if (closeRegister) {
    closeRegister.addEventListener('click', () => registerModal.classList.add('hidden'));
  }

  // Switch from Login -> Register
  if (openRegister) {
    openRegister.addEventListener('click', (e) => {
      e.preventDefault();
      loginModal.classList.add('hidden');
      registerModal.classList.remove('hidden');
    });
  }

  // Switch from Register -> Login
  if (switchToLogin) {
    switchToLogin.addEventListener('click', (e) => {
      e.preventDefault();
      registerModal.classList.add('hidden');
      loginModal.classList.remove('hidden');
    });
  }

  // Close modal by clicking outside
  if (loginModal) {
    loginModal.addEventListener('click', (e) => { 
      if(e.target === loginModal) loginModal.classList.add('hidden'); 
    });
  }
  
  if (registerModal) {
    registerModal.addEventListener('click', (e) => { 
      if(e.target === registerModal) registerModal.classList.add('hidden'); 
    });
  }
});