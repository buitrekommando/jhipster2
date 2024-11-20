import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ICentroSalud } from '../centro-salud.model';

@Component({
  standalone: true,
  selector: 'jhi-centro-salud-detail',
  templateUrl: './centro-salud-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CentroSaludDetailComponent {
  centroSalud = input<ICentroSalud | null>(null);

  previousState(): void {
    window.history.back();
  }
}
