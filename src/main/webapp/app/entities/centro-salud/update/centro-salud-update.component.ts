import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICentroSalud } from '../centro-salud.model';
import { CentroSaludService } from '../service/centro-salud.service';
import { CentroSaludFormGroup, CentroSaludFormService } from './centro-salud-form.service';

@Component({
  standalone: true,
  selector: 'jhi-centro-salud-update',
  templateUrl: './centro-salud-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CentroSaludUpdateComponent implements OnInit {
  isSaving = false;
  centroSalud: ICentroSalud | null = null;

  protected centroSaludService = inject(CentroSaludService);
  protected centroSaludFormService = inject(CentroSaludFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CentroSaludFormGroup = this.centroSaludFormService.createCentroSaludFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ centroSalud }) => {
      this.centroSalud = centroSalud;
      if (centroSalud) {
        this.updateForm(centroSalud);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const centroSalud = this.centroSaludFormService.getCentroSalud(this.editForm);
    if (centroSalud.id !== null) {
      this.subscribeToSaveResponse(this.centroSaludService.update(centroSalud));
    } else {
      this.subscribeToSaveResponse(this.centroSaludService.create(centroSalud));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICentroSalud>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(centroSalud: ICentroSalud): void {
    this.centroSalud = centroSalud;
    this.centroSaludFormService.resetForm(this.editForm, centroSalud);
  }
}
