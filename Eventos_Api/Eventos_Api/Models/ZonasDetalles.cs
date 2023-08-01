using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Eventos_Api.Models
{
    public partial class ZonasDetalles
    {
        [Key]
        public int id { get; set; }
        public int idEvento { get; set; }
        public string nombre { get; set; }
        public int capacidad { get; set; }
        public int compradas { get; set; }
        public double precio { get; set; }
        public int disponibles => capacidad - compradas;
        public double totalComprado => (capacidad - disponibles) * precio;

    }
}
