document
  .getElementById("registroMecanicoForm")
  .addEventListener("submit", async function (event) {
    event.preventDefault(); // Detiene el envío predeterminado del formulario

    // 1. Obtener valores de los campos
    const nombre = document.getElementById("mecanicoNombre").value.trim(); // .trim() para eliminar espacios en blanco
    const experiencia = document.getElementById("mecanicoExperiencia").value;
    const email = document.getElementById("mecanicoEmail").value.trim();
    const telefono = document.getElementById("mecanicoTelefono").value.trim();
    const password = document.getElementById("mecanicoPassword").value;
    const confirmPassword = document.getElementById(
      "mecanicoConfirmPassword"
    ).value;

    // 2. Realizar validaciones de datos en el cliente
    let isValid = true;
    let errorMessage = "";

    // Validación para 'nombre' (VARCHAR(100) NOT NULL)
    if (!nombre) {
      errorMessage += "El nombre completo es obligatorio.\n";
      isValid = false;
    } else if (nombre.length > 100) {
      errorMessage += "El nombre no puede exceder los 100 caracteres.\n";
      isValid = false;
    }

    // Validación para 'experiencia' (INT NOT NULL)
    const experienciaNum = parseInt(experiencia);
    if (isNaN(experienciaNum) || experienciaNum < 0) {
      errorMessage +=
        "Los años de experiencia deben ser un número entero positivo.\n";
      isValid = false;
    }

    // Validación para 'email' (VARCHAR(100) NOT NULL UNIQUE)
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Regex básica para email
    if (!email) {
      errorMessage += "El correo electrónico es obligatorio.\n";
      isValid = false;
    } else if (!emailRegex.test(email)) {
      errorMessage +=
        "Por favor, ingresa un formato de correo electrónico válido.\n";
      isValid = false;
    } else if (email.length > 100) {
      errorMessage +=
        "El correo electrónico no puede exceder los 100 caracteres.\n";
      isValid = false;
    }

    // Validación para 'telefono' (VARCHAR(20)) - Opcional, pero si se ingresa, validar formato o longitud
    if (telefono && telefono.length > 20) {
      errorMessage += "El teléfono no puede exceder los 20 caracteres.\n";
      isValid = false;
    }
    // Opcional: Podrías añadir una validación de regex para números de teléfono aquí si tienes un formato específico.

    // Validación de contraseñas
    if (!password) {
      errorMessage += "La contraseña es obligatoria.\n";
      isValid = false;
    } else if (password.length < 6) {
      // Ejemplo: mínimo 6 caracteres
      errorMessage += "La contraseña debe tener al menos 6 caracteres.\n";
      isValid = false;
    }

    if (password !== confirmPassword) {
      errorMessage +=
        "Las contraseñas no coinciden. Por favor, inténtalo de nuevo.\n";
      isValid = false;
    }

    if (!isValid) {
      alert(errorMessage);
      return; // Detiene el envío si hay errores de validación
    }

    // 3. Preparar los datos para enviar al servidor
    const mecanicoData = {
      nombre: nombre,
      experiencia: experienciaNum, // Ya parseado a número
      email: email,
      telefono: telefono,
      password: password,
      // Opcional: puedes enviar confirmPassword al backend si lo necesitas para una doble verificación
      confirmPassword: confirmPassword,
    };

    // 4. Enviar los datos al servidor
    try {
      const response = await fetch("/api/mecanicos", {
        // Asumiendo que tu endpoint es /api/mecanicos
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(mecanicoData),
      });

      if (response.ok) {
        // El servidor respondió con éxito (código 2xx)
        const newMecanico = await response.json();
        alert("¡Registro de mecánico exitoso!");
        console.log("Mecánico registrado:", newMecanico);
        window.location.href = "login.html"; // Redirige al usuario al login
      } else {
        // El servidor respondió con un error (código 4xx o 5xx)
        let errorData;
        try {
          errorData = await response.json(); // Intenta parsear el JSON de error
        } catch (jsonError) {
          // Si la respuesta no es un JSON válido, usa el texto de estado
          errorData = {
            message: response.statusText || "Error desconocido del servidor.",
          };
        }
        alert(
          `Error al registrar mecánico: ${
            errorData.message || response.statusText
          }`
        );
        console.error("Detalles del error del servidor:", errorData);
      }
    } catch (error) {
      // Error de red (por ejemplo, servidor no accesible)
      console.error("Error de red o del servidor:", error);
      alert(
        "Ocurrió un error al intentar registrar el mecánico. Por favor, revisa tu conexión."
      );
    }
  });
