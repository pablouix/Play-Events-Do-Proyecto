using Eventos_Api.Models;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;

namespace Eventos_Api.DATA
{
    public class Context : DbContext
    {
        public Context(DbContextOptions<Context> options) : base(options) { }

        public DbSet<Eventos> Eventos { get; set; }
        public DbSet<Boletas> Boletas { get; set; }
        public DbSet<Usuario> Usuarios { get; set; }
        public DbSet<ZonasDetalles> ZonasDetalles { get; set; } = default!;
    }
}
