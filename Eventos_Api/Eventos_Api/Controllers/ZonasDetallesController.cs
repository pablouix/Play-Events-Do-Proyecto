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
    public class ZonasDetallesController : ControllerBase
    {
        private readonly Context _context;

        public ZonasDetallesController(Context context)
        {
            _context = context;
        }

        // GET: api/ZonasDetalles
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ZonasDetalles>>> GetZonasDetalles()
        {
          if (_context.ZonasDetalles == null)
          {
              return NotFound();
          }
            return await _context.ZonasDetalles.ToListAsync();
        }

        // GET: api/ZonasDetalles/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ZonasDetalles>> GetZonasDetalles(int id)
        {
          if (_context.ZonasDetalles == null)
          {
              return NotFound();
          }
            var zonasDetalles = await _context.ZonasDetalles.FindAsync(id);

            if (zonasDetalles == null)
            {
                return NotFound();
            }

            return zonasDetalles;
        }

        // PUT: api/ZonasDetalles/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutZonasDetalles(int id, ZonasDetalles zonasDetalles)
        {
            if (id != zonasDetalles.id)
            {
                return BadRequest();
            }

            _context.Entry(zonasDetalles).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ZonasDetallesExists(id))
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

        // POST: api/ZonasDetalles
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<ZonasDetalles>> PostZonasDetalles(ZonasDetalles zonasDetalles)
        {
          if (_context.ZonasDetalles == null)
          {
              return Problem("Entity set 'Context.ZonasDetalles'  is null.");
          }
            _context.ZonasDetalles.Add(zonasDetalles);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetZonasDetalles", new { id = zonasDetalles.id }, zonasDetalles);
        }

        // DELETE: api/ZonasDetalles/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteZonasDetalles(int id)
        {
            if (_context.ZonasDetalles == null)
            {
                return NotFound();
            }
            var zonasDetalles = await _context.ZonasDetalles.FindAsync(id);
            if (zonasDetalles == null)
            {
                return NotFound();
            }

            _context.ZonasDetalles.Remove(zonasDetalles);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool ZonasDetallesExists(int id)
        {
            return (_context.ZonasDetalles?.Any(e => e.id == id)).GetValueOrDefault();
        }
    }
}
