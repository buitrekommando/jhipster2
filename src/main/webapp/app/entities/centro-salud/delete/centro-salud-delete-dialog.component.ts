import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICentroSalud } from '../centro-salud.model';
import { CentroSaludService } from '../service/centro-salud.service';

@Component({
  standalone: true,
  templateUrl: './centro-salud-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CentroSaludDeleteDialogComponent {
  centroSalud?: ICentroSalud;

  protected centroSaludService = inject(CentroSaludService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.centroSaludService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
