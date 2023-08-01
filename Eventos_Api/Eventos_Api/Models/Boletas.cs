using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace Eventos_Api.Models
{
    public class Boletas
    {
        [Key]
        public int Id { get; set; }
        public int IdZonaDetalle { get; set; }
        public int IdUsuario { get; set; }
        public DateTime FechaCompra { get; set; }
        public string NumeroAsiento { get; set; }
        public double Precio { get; set; }

        [ForeignKey("IdUsuario")]
        public Usuario Usuario { get; set; }

        [ForeignKey("IdZonaDetalle")]
        public ZonasDetalles ZonaDetalle { get; set; }
    }
}
