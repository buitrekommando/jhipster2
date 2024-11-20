import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IMedico } from '../medico.model';

@Component({
  standalone: true,
  selector: 'jhi-medico-detail',
  templateUrl: './medico-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MedicoDetailComponent {
  medico = input<IMedico | null>(null);

  previousState(): void {
    window.history.back();
  }
}
