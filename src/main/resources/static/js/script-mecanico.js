function openDetailsModal(idSolicitud) {
  // Get latitud and longitud from hidden inputs
  const latInput = document.getElementById('latitud-' + idSolicitud);
  const lonInput = document.getElementById('longitud-' + idSolicitud);
  const lat = latInput ? parseFloat(latInput.value) : null;
  const lon = lonInput ? parseFloat(lonInput.value) : null;

  let htmlContent = '<p>Ubicación no disponible</p>';
  if (lat !== null && lon !== null && !isNaN(lat) && !isNaN(lon)) {
    htmlContent = '<div id="map" style="height: 300px; width: 100%;"></div>';
  }

  Swal.fire({
    title: 'Detalles de la Solicitud',
    html: htmlContent,
    width: 600,
    showCloseButton: true,
    focusConfirm: false,
    confirmButtonText: 'Cerrar',
    didOpen: () => {
      if (lat !== null && lon !== null && !isNaN(lat) && !isNaN(lon)) {
        var map = L.map('map').setView([lat, lon], 15);
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          maxZoom: 19,
          attribution: '© OpenStreetMap'
        }).addTo(map);
        L.marker([lat, lon]).addTo(map)
          .bindPopup('Ubicación del conductor')
          .openPopup();
      }
    }
  });
}

function acceptRequest(id) {
  Swal.fire({
    title: "¿Aceptar solicitud?",
    text: "Confirma que puedes atender esta solicitud",
    icon: "question",
    showCancelButton: true,
    confirmButtonColor: "#ff8c00",
    cancelButtonColor: "#d33",
    confirmButtonText: "Sí, aceptar",
    cancelButtonText: "Cancelar",
  }).then((result) => {
    if (result.isConfirmed) {
      fetch('/dashboardmecanico/solicitud/' + id + '/aceptar', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
      })
      .then(response => response.text())
      .then(data => {
        if (data === 'OK') {
          Swal.fire({
            title: "¡Solicitud Aceptada!",
            text: "El cliente ha sido notificado. Dirígete a la ubicación.",
            icon: "success",
            confirmButtonColor: "#ff8c00",
          }).then(() => {
            // Reload the page to update the status
            location.reload();
          });
        } else {
          Swal.fire({
            title: "Error",
            text: "No se pudo actualizar el estado de la solicitud.",
            icon: "error",
            confirmButtonColor: "#ff8c00",
          });
        }
      })
      .catch(() => {
        Swal.fire({
          title: "Error",
          text: "Error al comunicarse con el servidor.",
          icon: "error",
          confirmButtonColor: "#ff8c00",
        });
      });
    }
  });
}

function rejectRequest(id) {
  Swal.fire({
    title: "¿Rechazar solicitud?",
    text: "Esta solicitud será ofrecida a otros mecánicos",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#d33",
    cancelButtonColor: "#6b7280",
    confirmButtonText: "Sí, rechazar",
    cancelButtonText: "Cancelar",
  }).then((result) => {
    if (result.isConfirmed) {
      fetch('/dashboardmecanico/solicitud/' + id + '/rechazar', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
      })
      .then(response => response.text())
      .then(data => {
        if (data === 'OK') {
          Swal.fire({
            title: "Solicitud Rechazada",
            text: "La solicitud ha sido enviada a otros mecánicos",
            icon: "info",
            confirmButtonColor: "#ff8c00",
          }).then(() => {
            // Reload the page to update the status and remove the request
            location.reload();
          });
        } else {
          Swal.fire({
            title: "Error",
            text: "No se pudo rechazar la solicitud.",
            icon: "error",
            confirmButtonColor: "#ff8c00",
          });
        }
      })
      .catch(() => {
        Swal.fire({
          title: "Error",
          text: "Error al comunicarse con el servidor.",
          icon: "error",
          confirmButtonColor: "#ff8c00",
        });
      });
    }
  });
}

function finalizeRequest(id) {
  Swal.fire({
    title: "¿Finalizar servicio?",
    text: "Confirma que deseas finalizar este servicio",
    icon: "question",
    showCancelButton: true,
    confirmButtonColor: "#3b82f6",
    cancelButtonColor: "#6b7280",
    confirmButtonText: "Sí, finalizar",
    cancelButtonText: "Cancelar",
  }).then((result) => {
    if (result.isConfirmed) {
      fetch('/dashboardmecanico/solicitud/' + id + '/finalizar', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
      })
      .then(response => response.text())
      .then(data => {
        if (data === 'OK') {
          Swal.fire({
            title: "¡Servicio Finalizado!",
            text: "El servicio ha sido marcado como finalizado.",
            icon: "success",
            confirmButtonColor: "#3b82f6",
          }).then(() => {
            // Reload the page to update the status and buttons
            location.reload();
          });
        } else {
          Swal.fire({
            title: "Error",
            text: "No se pudo finalizar el servicio.",
            icon: "error",
            confirmButtonColor: "#3b82f6",
          });
        }
      })
      .catch(() => {
        Swal.fire({
          title: "Error",
          text: "Error al comunicarse con el servidor.",
          icon: "error",
          confirmButtonColor: "#3b82f6",
        });
      });
    }
  });
}
