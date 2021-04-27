mod utils;

use std::fmt;
use wasm_bindgen::prelude::*;

// When the `wee_alloc` feature is enabled, use `wee_alloc` as the global
// allocator.
#[cfg(feature = "wee_alloc")]
#[global_allocator]
static ALLOC: wee_alloc::WeeAlloc = wee_alloc::WeeAlloc::INIT;

#[wasm_bindgen]
#[repr(u8)]
#[derive(Clone, Copy, Debug, PartialEq, Eq)]
pub enum Cell {
    Dead = 0,
    Alive = 1,
}

#[wasm_bindgen]
pub struct Universe {
    width: u32,
    height: u32,
    cells: Vec<Cell>,
}

#[wasm_bindgen]
impl Universe {
    pub fn width(&self) -> u32 {
        self.width
    }

    pub fn height(&self) -> u32 {
        self.height
    }

    pub fn cells(&self) -> *const Cell {
        self.cells.as_ptr()
    }

    pub fn tick(&mut self) {
        let mut next_state = self.cells.clone();
        for row in 0..self.height {
            for col in 0..self.width {
                let cell_idx = self.get_index(row, col);
                let cur_cell = self.cells[cell_idx];
                let ln_count = self.live_neighbor_count(row, col);

                next_state[cell_idx] = Universe::next_cell_state(cur_cell, ln_count)
            }
        }
        self.cells = next_state;
    }

    pub fn new() -> Universe {
        let width = 256;
        let height = 156;

        let cells = (0..width * height)
            .map(|i| {
                if i % 2 == 0 || i % 7 == 0 {
                    Cell::Alive
                } else {
                    Cell::Dead
                }
            })
            .collect();
        return Universe {
            width,
            height,
            cells,
        };
    }

    pub fn render(&self) -> String {
        self.to_string()
    }
}

impl Universe {
    fn get_index(&self, row: u32, col: u32) -> usize {
        (row * self.width + col) as usize
    }

    fn live_neighbor_count(&self, row: u32, col: u32) -> u8 {
        let mut count = 0;
        for d_row in [self.height - 1, 0, 1].iter().cloned() {
            for d_col in [self.width - 1, 0, 1].iter().cloned() {
                if !(d_row == 0 && d_col == 0) {
                    // Accounts for edge cases generically
                    let neighbor_row = (row + d_row) % self.height;
                    let neighbor_col = (col + d_col) % self.width;
                    count += self.cells[self.get_index(neighbor_row, neighbor_col)] as u8
                }
            }
        }
        return count;
    }

    fn next_cell_state(cell: Cell, live_neighbor_count: u8) -> Cell {
        match (cell, live_neighbor_count) {
            // Rule 1: Any live cell with fewer than two live neighbours
            // dies, as if caused by underpopulation.
            (Cell::Alive, x) if x < 2 => Cell::Dead,
            // Rule 2: Any live cell with two or three live neighbours
            // lives on to the next generation.
            (Cell::Alive, x) if [2, 3].contains(&x) => Cell::Alive,
            // Rule 3: Any live cell with more than three live
            // neighbours dies, as if by overpopulation.
            (Cell::Alive, x) if x > 3 => Cell::Dead,
            // Rule 4: Any dead cell with exactly three live neighbours
            // becomes a live cell, as if by reproduction.
            (Cell::Dead, 3) => Cell::Alive,
            // All other cells remain in the same state.
            (otherwise, _) => otherwise,
        }
    }
}

impl fmt::Display for Universe {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        for line in self.cells.as_slice().chunks(self.width as usize) {
            for &cell in line {
                let symbol = if cell == Cell::Dead { '◻' } else { '◼' };
                write!(f, "{}", symbol)?;
            }
            write!(f, "\n")?;
        }
        Ok(())
    }
}
