using System.ComponentModel.DataAnnotations;

namespace Eventos_Api.Models
{
    public class Usuario
    {
        [Key]
        public int Id { get; set; }
        public string Nombre { get; set; }
        public string Contrasena { get; set; }
        public string Email { get; set; }
        public string Telefono { get; set;}
    }
}
