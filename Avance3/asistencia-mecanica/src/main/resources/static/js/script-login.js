const radioUsuario = document.getElementById("radioUsuario");
const radioMecanico = document.getElementById("radioMecanico");
const registerLink = document.getElementById("registerLink");

function updateRegisterLink() {
  if (radioUsuario.checked) {
    registerLink.href = "registroconductor.html";
  } else if (radioMecanico.checked) {
    registerLink.href = "registromecanico.html";
  }
}

radioUsuario.addEventListener("change", updateRegisterLink);
radioMecanico.addEventListener("change", updateRegisterLink);

updateRegisterLink();
