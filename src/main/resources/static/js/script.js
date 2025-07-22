function openRatingModal(idSolicitud) {
  const modalHtml = `
    <div id="ratingModal" class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-md w-full p-6">
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-lg font-semibold">Calificar Servicio</h3>
          <button onclick="closeRatingModal()" class="text-gray-500 hover:text-gray-700">
            <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20">
              <path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"/>
            </svg>
          </button>
        </div>
        <div class="text-center mb-4">
          <p>Seleccione la calificación para el servicio:</p>
          <div id="starRating" class="flex justify-center space-x-1 mt-2">
            <button class="star text-gray-400 text-3xl" data-value="1">&#9733;</button>
            <button class="star text-gray-400 text-3xl" data-value="2">&#9733;</button>
            <button class="star text-gray-400 text-3xl" data-value="3">&#9733;</button>
            <button class="star text-gray-400 text-3xl" data-value="4">&#9733;</button>
            <button class="star text-gray-400 text-3xl" data-value="5">&#9733;</button>
          </div>
        </div>
        <div class="flex space-x-3 mt-6">
          <button id="submitRatingBtn" class="flex-1 bg-primary hover:bg-primary-dark text-white py-2 px-4 rounded-lg transition-colors">Enviar</button>
          <button onclick="closeRatingModal()" class="flex-1 border border-gray-300 text-gray-700 hover:bg-gray-50 py-2 px-4 rounded-lg transition-colors">Cancelar</button>
        </div>
      </div>
    </div>
  `;
  document.body.insertAdjacentHTML('beforeend', modalHtml);

  let selectedRating = 0;
  const stars = document.querySelectorAll('#starRating .star');
  stars.forEach(star => {
    star.addEventListener('click', () => {
      selectedRating = parseInt(star.getAttribute('data-value'));
      stars.forEach(s => {
        s.classList.toggle('text-yellow-400', parseInt(s.getAttribute('data-value')) <= selectedRating);
        s.classList.toggle('text-gray-400', parseInt(s.getAttribute('data-value')) > selectedRating);
      });
    });
  });

  document.getElementById('submitRatingBtn').addEventListener('click', () => {
    if (selectedRating === 0) {
      alert('Por favor, seleccione una calificación.');
      return;
    }
    fetch('/dashboardconductor/solicitar-calificacion', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      body: new URLSearchParams({
        idSolicitud: idSolicitud,
        calificacion: selectedRating
      })
    }).then(response => {
      if (response.ok) {
        closeRatingModal();
        Swal.fire({
          title: '¡Calificación enviada!',
          icon: 'success',
          confirmButtonColor: '#ff8c00'
        }).then(() => {
          location.reload();
        });
      } else {
        Swal.fire({
          title: 'Error',
          text: 'No se pudo enviar la calificación. Intente nuevamente.',
          icon: 'error',
          confirmButtonColor: '#ff8c00'
        });
      }
    }).catch(() => {
      Swal.fire({
        title: 'Error',
        text: 'Error al comunicarse con el servidor.',
        icon: 'error',
        confirmButtonColor: '#ff8c00'
      });
    });
  });
}

function closeRatingModal() {
  const modal = document.getElementById('ratingModal');
  if (modal) {
    modal.remove();
  }
}
