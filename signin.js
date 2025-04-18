function goToNext() {
    const emailInput = document.getElementById('email');
  
    if (!emailInput.checkValidity()) {
      alert("Please enter a valid Gmail address.");
      return;
    }
  
    const email = emailInput.value;
    const namePart = email.split('@')[0]; // "aryan333"
    const lettersOnly = namePart.replace(/[^a-zA-Z]/g, ''); // "aryan"
  
    const container = document.getElementById('formContainer');
  
    container.innerHTML = `
      <div class="text1">
        <h1>Hi <span style="color: #2bff2b;">${lettersOnly}</span>!</h1>
      </div>
      <input
        type="date"
        id="dob"
        class="dob-input"
        required
      />
  
      <div class="name-row">
        <input type="text" id="fname" placeholder="First Name" required />
        <input type="text" id="mname" placeholder="Middle Name" />
        <input type="text" id="lname" placeholder="Last Name" required />
      </div>
  
      <button class="Sign-Up" onclick="letsMoveOn()">Let's Move On</button>
    `;
  }
  
function letsMoveOn() {
    const dob = document.getElementById('dob').value;
    const fname = document.getElementById('fname').value;
    const mname = document.getElementById('mname').value;
    const lname = document.getElementById('lname').value;
  
    if (!dob || !fname || !lname) {
      alert("Please fill out all required fields.");
      return;
    }
  
    // Redirect to video page
    window.location.href = "main1.html";
  }
  