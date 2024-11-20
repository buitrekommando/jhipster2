import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { IMedico } from 'app/entities/medico/medico.model';
import { MedicoService } from 'app/entities/medico/service/medico.service';
import { ICentroSalud } from 'app/entities/centro-salud/centro-salud.model';
import { CentroSaludService } from 'app/entities/centro-salud/service/centro-salud.service';
import { ReservaService } from '../service/reserva.service';
import { IReserva } from '../reserva.model';
import { ReservaFormGroup, ReservaFormService } from './reserva-form.service';

@Component({
  standalone: true,
  selector: 'jhi-reserva-update',
  templateUrl: './reserva-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ReservaUpdateComponent implements OnInit {
  isSaving = false;
  reserva: IReserva | null = null;

  pacientesSharedCollection: IPaciente[] = [];
  medicosSharedCollection: IMedico[] = [];
  centroSaludsSharedCollection: ICentroSalud[] = [];

  protected reservaService = inject(ReservaService);
  protected reservaFormService = inject(ReservaFormService);
  protected pacienteService = inject(PacienteService);
  protected medicoService = inject(MedicoService);
  protected centroSaludService = inject(CentroSaludService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ReservaFormGroup = this.reservaFormService.createReservaFormGroup();

  comparePaciente = (o1: IPaciente | null, o2: IPaciente | null): boolean => this.pacienteService.comparePaciente(o1, o2);

  compareMedico = (o1: IMedico | null, o2: IMedico | null): boolean => this.medicoService.compareMedico(o1, o2);

  compareCentroSalud = (o1: ICentroSalud | null, o2: ICentroSalud | null): boolean => this.centroSaludService.compareCentroSalud(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reserva }) => {
      this.reserva = reserva;
      if (reserva) {
        this.updateForm(reserva);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reserva = this.reservaFormService.getReserva(this.editForm);
    if (reserva.id !== null) {
      this.subscribeToSaveResponse(this.reservaService.update(reserva));
    } else {
      this.subscribeToSaveResponse(this.reservaService.create(reserva));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReserva>>): void {
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

  protected updateForm(reserva: IReserva): void {
    this.reserva = reserva;
    this.reservaFormService.resetForm(this.editForm, reserva);

    this.pacientesSharedCollection = this.pacienteService.addPacienteToCollectionIfMissing<IPaciente>(
      this.pacientesSharedCollection,
      reserva.paciente,
    );
    this.medicosSharedCollection = this.medicoService.addMedicoToCollectionIfMissing<IMedico>(this.medicosSharedCollection, reserva.medico);
    this.centroSaludsSharedCollection = this.centroSaludService.addCentroSaludToCollectionIfMissing<ICentroSalud>(
      this.centroSaludsSharedCollection,
      reserva.centroSalud,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pacienteService
      .query()
      .pipe(map((res: HttpResponse<IPaciente[]>) => res.body ?? []))
      .pipe(
        map((pacientes: IPaciente[]) =>
          this.pacienteService.addPacienteToCollectionIfMissing<IPaciente>(pacientes, this.reserva?.paciente),
        ),
      )
      .subscribe((pacientes: IPaciente[]) => (this.pacientesSharedCollection = pacientes));

    this.medicoService
      .query()
      .pipe(map((res: HttpResponse<IMedico[]>) => res.body ?? []))
      .pipe(map((medicos: IMedico[]) => this.medicoService.addMedicoToCollectionIfMissing<IMedico>(medicos, this.reserva?.medico)))
      .subscribe((medicos: IMedico[]) => (this.medicosSharedCollection = medicos));

    this.centroSaludService
      .query()
      .pipe(map((res: HttpResponse<ICentroSalud[]>) => res.body ?? []))
      .pipe(
        map((centroSaluds: ICentroSalud[]) =>
          this.centroSaludService.addCentroSaludToCollectionIfMissing<ICentroSalud>(centroSaluds, this.reserva?.centroSalud),
        ),
      )
      .subscribe((centroSaluds: ICentroSalud[]) => (this.centroSaludsSharedCollection = centroSaluds));
  }
}
