using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Eventos_Api.Models
{
    public partial class Eventos
    {
        [Key]
        public int id { get; set; }
        public string imagen { get; set; }
        public string nombre { get; set; }
        public string fecha { get; set; }
        public string hora { get; set; }
        public string Descripcion { get; set; } 
        public string Lugar { get; set; }
        public string Organizador { get; set; }
        public bool EsPublico { get; set; } // público o requiere invitación

        [ForeignKey("idEvento")]
        public List<ZonasDetalles> ZonaDetalles { get; set; } = new List<ZonasDetalles>();
    }
}
