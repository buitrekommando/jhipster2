import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMedico } from 'app/entities/medico/medico.model';
import { MedicoService } from 'app/entities/medico/service/medico.service';
import { ICentroSalud } from 'app/entities/centro-salud/centro-salud.model';
import { CentroSaludService } from 'app/entities/centro-salud/service/centro-salud.service';
import { PacienteService } from '../service/paciente.service';
import { IPaciente } from '../paciente.model';
import { PacienteFormGroup, PacienteFormService } from './paciente-form.service';

@Component({
  standalone: true,
  selector: 'jhi-paciente-update',
  templateUrl: './paciente-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PacienteUpdateComponent implements OnInit {
  isSaving = false;
  paciente: IPaciente | null = null;

  medicosSharedCollection: IMedico[] = [];
  centroSaludsSharedCollection: ICentroSalud[] = [];

  protected pacienteService = inject(PacienteService);
  protected pacienteFormService = inject(PacienteFormService);
  protected medicoService = inject(MedicoService);
  protected centroSaludService = inject(CentroSaludService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PacienteFormGroup = this.pacienteFormService.createPacienteFormGroup();

  compareMedico = (o1: IMedico | null, o2: IMedico | null): boolean => this.medicoService.compareMedico(o1, o2);

  compareCentroSalud = (o1: ICentroSalud | null, o2: ICentroSalud | null): boolean => this.centroSaludService.compareCentroSalud(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paciente }) => {
      this.paciente = paciente;
      if (paciente) {
        this.updateForm(paciente);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paciente = this.pacienteFormService.getPaciente(this.editForm);
    if (paciente.id !== null) {
      this.subscribeToSaveResponse(this.pacienteService.update(paciente));
    } else {
      this.subscribeToSaveResponse(this.pacienteService.create(paciente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaciente>>): void {
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

  protected updateForm(paciente: IPaciente): void {
    this.paciente = paciente;
    this.pacienteFormService.resetForm(this.editForm, paciente);

    this.medicosSharedCollection = this.medicoService.addMedicoToCollectionIfMissing<IMedico>(
      this.medicosSharedCollection,
      paciente.medico,
    );
    this.centroSaludsSharedCollection = this.centroSaludService.addCentroSaludToCollectionIfMissing<ICentroSalud>(
      this.centroSaludsSharedCollection,
      paciente.centroSalud,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.medicoService
      .query()
      .pipe(map((res: HttpResponse<IMedico[]>) => res.body ?? []))
      .pipe(map((medicos: IMedico[]) => this.medicoService.addMedicoToCollectionIfMissing<IMedico>(medicos, this.paciente?.medico)))
      .subscribe((medicos: IMedico[]) => (this.medicosSharedCollection = medicos));

    this.centroSaludService
      .query()
      .pipe(map((res: HttpResponse<ICentroSalud[]>) => res.body ?? []))
      .pipe(
        map((centroSaluds: ICentroSalud[]) =>
          this.centroSaludService.addCentroSaludToCollectionIfMissing<ICentroSalud>(centroSaluds, this.paciente?.centroSalud),
        ),
      )
      .subscribe((centroSaluds: ICentroSalud[]) => (this.centroSaludsSharedCollection = centroSaluds));
  }
}
