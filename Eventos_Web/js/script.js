function generateList() {
  const lista = document.getElementById("lista");
  lista.innerHTML = "";

  fetch("http://eventos.somee.com/api/Eventos")
    .then(response => response.json())
    .then(data => {
      data.forEach(evento => {
        const eventoLi = document.createElement("li");

        const nombreP = document.createElement("p");
        nombreP.textContent = evento.nombre;
        eventoLi.appendChild(nombreP);

        const descripcionP = document.createElement("p");
        descripcionP.textContent = evento.descripcion;
        eventoLi.appendChild(descripcionP);

        const lugarP = document.createElement("p");
        lugarP.textContent = "Lugar: " + evento.lugar;
        eventoLi.appendChild(lugarP);

        const organizadorP = document.createElement("p");
        organizadorP.textContent = evento.organizador;
        eventoLi.appendChild(organizadorP);

        const fechaP = document.createElement("p");
        const fecha = new Date(evento.fecha);
        fechaP.textContent = "Fecha: " + fecha.toLocaleDateString();
        eventoLi.appendChild(fechaP);

        const horaP = document.createElement("p");
        horaP.textContent = "Hora: " + evento.hora;
        eventoLi.appendChild(horaP);

        lista.appendChild(eventoLi);

        const zonasUl = document.createElement("ul");
        zonasUl.classList.add("hidden");
        evento.zonaDetalles.forEach((zona, index) => {
          const zonaLi = document.createElement("li");

          const nombreLi = document.createElement("li");
          nombreLi.textContent = "Zona: " + zona.nombre;
          zonaLi.appendChild(nombreLi);

          const capacidadLi = document.createElement("li");
          capacidadLi.textContent = "Capacidad: " + zona.capacidad + " Personas";
          zonaLi.appendChild(capacidadLi);

          const precioLi = document.createElement("li");
          precioLi.textContent = "Precio: " + zona.precio + " DOP";
          zonaLi.appendChild(precioLi);

          const capacidadDiv = document.createElement("div");
          capacidadDiv.classList.add("capacidad-box");
          zonaLi.appendChild(capacidadDiv);

          const comprarButton = document.createElement("button");
          comprarButton.textContent = "Comprar boletas";
          comprarButton.id = "comprarButton" + index;
          capacidadDiv.appendChild(comprarButton);

          if (zona.capacidad > 0) {
            comprarButton.disabled = false;
          } else {
            comprarButton.disabled = true;
          }

          comprarButton.addEventListener("click", () => {
            if (zona.capacidad > 0) {
              showCompraModal(zona.nombre, zona.disponibles, zona);
            } else {
              console.log("No hay capacidad disponible para la zona: " + zona.nombre);
            }
          });

          zonasUl.appendChild(zonaLi);
        });

        eventoLi.appendChild(zonasUl);

        const toggleButton = document.createElement("button");
        toggleButton.textContent = "Mostrar Zona";
        eventoLi.appendChild(toggleButton);

        toggleButton.addEventListener("click", () => {
          zonasUl.classList.toggle("hidden");
          if (toggleButton.textContent === "Mostrar Zona") {
            toggleButton.textContent = "Ocultar Zona";
          } else {
            toggleButton.textContent = "Mostrar Zona";
          }
        });

        const esPublicoP = document.createElement("p");
        if (evento.esPublico) {
          esPublicoP.textContent = "Evento Público";
        } else {
          esPublicoP.textContent = "Evento Privado";
        }
        eventoLi.appendChild(esPublicoP);
      });
    })
    .catch(error => {
      console.error("Error al obtener los datos de la API:", error);
    });
}

function showCompraModal(zonaNombre, disponibles, zona) {
  const modal = document.getElementById("modal");
  const modalContent = document.querySelector(".modal-content");
  const h2 = document.createElement("h2");
  h2.textContent = "Seleccionar cantidad de boletas " + zonaNombre;
  modalContent.insertBefore(h2, modalContent.firstChild);

  const disponiblesP = document.createElement("p");
  disponiblesP.textContent = "Boletas disponibles: " + disponibles;
  modalContent.insertBefore(disponiblesP, h2.nextSibling);

  const realizarCompraButton = document.getElementById("realizarCompra");
  realizarCompraButton.addEventListener("click", () => {
    const cantidadBoletasInput = document.getElementById("cantidadBoletas");
    const cantidadBoletas = parseInt(cantidadBoletasInput.value);

    if (cantidadBoletas <= 0) {
      console.log("La cantidad de boletas debe ser mayor a cero.");
      return;
    }

    if (cantidadBoletas > disponibles) {
      console.log("La cantidad de boletas supera la disponibilidad disponible.");
      return;
    }

    // Actualizar la cantidad de boletas compradas en la zona seleccionada
    zona.compradas += cantidadBoletas;
    zona.disponibles = zona.capacidad - zona.compradas - cantidadBoletas;

    // Realizar la solicitud PUT a la API para actualizar los detalles de zona
    fetch("http://eventos.somee.com/api/ZonasDetalles/" + zona.id, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(zona)
    })
      .then(response => response.json())
      .then(updatedZona => {
        console.log("Boletas compradas para la zona: " + zonaNombre + ", Cantidad: " + cantidadBoletas);
        console.log("Detalles de zona actualizados:", updatedZona);

        modal.style.display = "none";
        modalContent.removeChild(h2);
        modalContent.removeChild(disponiblesP);
        cantidadBoletasInput.value = 1;
      })
      .catch(error => {
        console.error("Error al actualizar los detalles de zona:", error);
      })
      .finally(() => {
        closeModal(); // Cerrar el modal después de realizar la compra
        location.reload(); // Recargar la página después de la compra
      });
  });

  let closeButton = document.getElementById("modalCloseButton");
  if (!closeButton) {
    closeButton = document.createElement("button");
    closeButton.textContent = "Cerrar";
    closeButton.id = "modalCloseButton";
    closeButton.addEventListener("click", () => {
      closeModal(); // Cerrar el modal al hacer clic en el botón "Cerrar"
    });
    modalContent.appendChild(closeButton);
  }

  modal.style.display = "block";
}

function closeModal() {
  const modal = document.getElementById("modal");
  const modalContent = document.querySelector(".modal-content");
  const h2 = document.querySelector("#modal h2");
  const disponiblesP = document.querySelector("#modal p");
  const cantidadBoletasInput = document.getElementById("cantidadBoletas");

  modal.style.display = "none";
  modalContent.removeChild(h2);
  modalContent.removeChild(disponiblesP);
  cantidadBoletasInput.value = 1;
}


generateList();