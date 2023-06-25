function animateNumber(element, start, end) {
    let current = start;
    const increment = Math.ceil((end - start) / 50);

    const timer = setInterval(() => {
      current += increment;
      element.textContent = current;

      if (current >= end) {
        element.textContent = end;
        clearInterval(timer);
      }
    }, 50);
  }

  window.onload = function() {
    const ventasElement = document.getElementById('ventas');
    const pedidosElement = document.getElementById('pedidos');
    const ticketsElement = document.getElementById('tickets');

    animateNumber(ventasElement, 0, 2300);
    animateNumber(pedidosElement, 0, 15);
    animateNumber(ticketsElement, 0, 290);
  };