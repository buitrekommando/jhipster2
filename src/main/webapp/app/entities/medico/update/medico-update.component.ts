import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICentroSalud } from 'app/entities/centro-salud/centro-salud.model';
import { CentroSaludService } from 'app/entities/centro-salud/service/centro-salud.service';
import { IMedico } from '../medico.model';
import { MedicoService } from '../service/medico.service';
import { MedicoFormGroup, MedicoFormService } from './medico-form.service';

@Component({
  standalone: true,
  selector: 'jhi-medico-update',
  templateUrl: './medico-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MedicoUpdateComponent implements OnInit {
  isSaving = false;
  medico: IMedico | null = null;

  centroSaludsSharedCollection: ICentroSalud[] = [];

  protected medicoService = inject(MedicoService);
  protected medicoFormService = inject(MedicoFormService);
  protected centroSaludService = inject(CentroSaludService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MedicoFormGroup = this.medicoFormService.createMedicoFormGroup();

  compareCentroSalud = (o1: ICentroSalud | null, o2: ICentroSalud | null): boolean => this.centroSaludService.compareCentroSalud(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medico }) => {
      this.medico = medico;
      if (medico) {
        this.updateForm(medico);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medico = this.medicoFormService.getMedico(this.editForm);
    if (medico.id !== null) {
      this.subscribeToSaveResponse(this.medicoService.update(medico));
    } else {
      this.subscribeToSaveResponse(this.medicoService.create(medico));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedico>>): void {
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

  protected updateForm(medico: IMedico): void {
    this.medico = medico;
    this.medicoFormService.resetForm(this.editForm, medico);

    this.centroSaludsSharedCollection = this.centroSaludService.addCentroSaludToCollectionIfMissing<ICentroSalud>(
      this.centroSaludsSharedCollection,
      medico.centroSalud,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.centroSaludService
      .query()
      .pipe(map((res: HttpResponse<ICentroSalud[]>) => res.body ?? []))
      .pipe(
        map((centroSaluds: ICentroSalud[]) =>
          this.centroSaludService.addCentroSaludToCollectionIfMissing<ICentroSalud>(centroSaluds, this.medico?.centroSalud),
        ),
      )
      .subscribe((centroSaluds: ICentroSalud[]) => (this.centroSaludsSharedCollection = centroSaluds));
  }
}
