import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IReserva } from '../reserva.model';

@Component({
  standalone: true,
  selector: 'jhi-reserva-detail',
  templateUrl: './reserva-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ReservaDetailComponent {
  reserva = input<IReserva | null>(null);

  previousState(): void {
    window.history.back();
  }
}
