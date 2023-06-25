using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Eventos_Api.DATA;
using Eventos_Api.Models;

namespace Eventos_Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class EventosController : ControllerBase
    {
        private readonly Context _context;

        public EventosController(Context context)
        {
            _context = context;
        }

        // GET: api/Eventos
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Eventos>>> GetEventos()
        {
          if (_context.Eventos == null)
          {
              return NotFound();
          }
            return await _context.Eventos.Include(e => e.ZonaDetalles).ToListAsync();
        }

        // GET: api/Eventos/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Eventos>> GetEventos(int id)
        {
          if (_context.Eventos == null)
          {
              return NotFound();
          }
            var eventos = await _context.Eventos.Include(e => e.ZonaDetalles).FirstOrDefaultAsync(e => e.id == id);

            if (eventos == null)
            {
                return NotFound();
            }

            return eventos;
        }

        // PUT: api/Eventos/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutEventos(int id, Eventos eventos)
        {
            if (id != eventos.id)
            {
                return BadRequest();
            }

            _context.Entry(eventos).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!EventosExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Eventos
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Eventos>> PostEventos(Eventos eventos)
        {
          if (_context.Eventos == null)
          {
              return Problem("Entity set 'Context.Eventos'  is null.");
          }
            _context.Eventos.Add(eventos);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetEventos", new { id = eventos.id }, eventos);
        }

        // DELETE: api/Eventos/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteEventos(int id)
        {
            if (_context.Eventos == null)
            {
                return NotFound();
            }
            var eventos = await _context.Eventos.FindAsync(id);
            if (eventos == null)
            {
                return NotFound();
            }

            _context.Eventos.Remove(eventos);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool EventosExists(int id)
        {
            return (_context.Eventos?.Any(e => e.id == id)).GetValueOrDefault();
        }
    }
}
