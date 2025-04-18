const dropArea = document.getElementById('dropArea');
  const videoInput = document.getElementById('videoInput');

  // Prevent default behaviors
  ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
    dropArea.addEventListener(eventName, e => e.preventDefault());
  });

  // Highlight on drag
  ['dragenter', 'dragover'].forEach(eventName => {
    dropArea.addEventListener(eventName, () => dropArea.classList.add('dragover'));
  });

  // Remove highlight
  ['dragleave', 'drop'].forEach(eventName => {
    dropArea.addEventListener(eventName, () => dropArea.classList.remove('dragover'));
  });

  // Handle drop
  dropArea.addEventListener('drop', e => {
    const file = e.dataTransfer.files[0];
    if (file && file.type.startsWith('video/')) {
      videoInput.files = e.dataTransfer.files;
      console.log("Video file selected via drag & drop:", file.name);
    }
  });

  // Optional: clicking label triggers input
  document.querySelector('.upload-label').addEventListener('click', () => {
    videoInput.click();
  });