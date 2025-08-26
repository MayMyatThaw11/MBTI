// Floating blobs
document.addEventListener('DOMContentLoaded', function() {
  const colors = ['rgba(79,70,229,0.25)','rgba(16,185,129,0.25)','rgba(59,130,246,0.2)'];
  
  for(let i = 0; i < 5; i++) {
    const blob = document.createElement('div');
    blob.classList.add('blob');
    blob.style.width = 300 + Math.random() * 400 + 'px';
    blob.style.height = 300 + Math.random() * 400 + 'px';
    blob.style.background = colors[i % colors.length];
    blob.style.left = Math.random() * 80 + 'vw';
    blob.style.top = Math.random() * 80 + 'vh';
    blob.style.animationDuration = 30 + Math.random() * 20 + 's';
    blob.style.animationDelay = Math.random() * 10 + 's';
    blob.style.zIndex = '-1';
    document.body.appendChild(blob);
  }
});