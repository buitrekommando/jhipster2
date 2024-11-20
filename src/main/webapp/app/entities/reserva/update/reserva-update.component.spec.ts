import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { IMedico } from 'app/entities/medico/medico.model';
import { MedicoService } from 'app/entities/medico/service/medico.service';
import { ICentroSalud } from 'app/entities/centro-salud/centro-salud.model';
import { CentroSaludService } from 'app/entities/centro-salud/service/centro-salud.service';
import { IReserva } from '../reserva.model';
import { ReservaService } from '../service/reserva.service';
import { ReservaFormService } from './reserva-form.service';

import { ReservaUpdateComponent } from './reserva-update.component';

describe('Reserva Management Update Component', () => {
  let comp: ReservaUpdateComponent;
  let fixture: ComponentFixture<ReservaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reservaFormService: ReservaFormService;
  let reservaService: ReservaService;
  let pacienteService: PacienteService;
  let medicoService: MedicoService;
  let centroSaludService: CentroSaludService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReservaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ReservaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReservaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reservaFormService = TestBed.inject(ReservaFormService);
    reservaService = TestBed.inject(ReservaService);
    pacienteService = TestBed.inject(PacienteService);
    medicoService = TestBed.inject(MedicoService);
    centroSaludService = TestBed.inject(CentroSaludService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Paciente query and add missing value', () => {
      const reserva: IReserva = { id: 456 };
      const paciente: IPaciente = { id: 20457 };
      reserva.paciente = paciente;

      const pacienteCollection: IPaciente[] = [{ id: 29994 }];
      jest.spyOn(pacienteService, 'query').mockReturnValue(of(new HttpResponse({ body: pacienteCollection })));
      const additionalPacientes = [paciente];
      const expectedCollection: IPaciente[] = [...additionalPacientes, ...pacienteCollection];
      jest.spyOn(pacienteService, 'addPacienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ reserva });
      comp.ngOnInit();

      expect(pacienteService.query).toHaveBeenCalled();
      expect(pacienteService.addPacienteToCollectionIfMissing).toHaveBeenCalledWith(
        pacienteCollection,
        ...additionalPacientes.map(expect.objectContaining),
      );
      expect(comp.pacientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Medico query and add missing value', () => {
      const reserva: IReserva = { id: 456 };
      const medico: IMedico = { id: 8891 };
      reserva.medico = medico;

      const medicoCollection: IMedico[] = [{ id: 21385 }];
      jest.spyOn(medicoService, 'query').mockReturnValue(of(new HttpResponse({ body: medicoCollection })));
      const additionalMedicos = [medico];
      const expectedCollection: IMedico[] = [...additionalMedicos, ...medicoCollection];
      jest.spyOn(medicoService, 'addMedicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ reserva });
      comp.ngOnInit();

      expect(medicoService.query).toHaveBeenCalled();
      expect(medicoService.addMedicoToCollectionIfMissing).toHaveBeenCalledWith(
        medicoCollection,
        ...additionalMedicos.map(expect.objectContaining),
      );
      expect(comp.medicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CentroSalud query and add missing value', () => {
      const reserva: IReserva = { id: 456 };
      const centroSalud: ICentroSalud = { id: 1278 };
      reserva.centroSalud = centroSalud;

      const centroSaludCollection: ICentroSalud[] = [{ id: 6855 }];
      jest.spyOn(centroSaludService, 'query').mockReturnValue(of(new HttpResponse({ body: centroSaludCollection })));
      const additionalCentroSaluds = [centroSalud];
      const expectedCollection: ICentroSalud[] = [...additionalCentroSaluds, ...centroSaludCollection];
      jest.spyOn(centroSaludService, 'addCentroSaludToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ reserva });
      comp.ngOnInit();

      expect(centroSaludService.query).toHaveBeenCalled();
      expect(centroSaludService.addCentroSaludToCollectionIfMissing).toHaveBeenCalledWith(
        centroSaludCollection,
        ...additionalCentroSaluds.map(expect.objectContaining),
      );
      expect(comp.centroSaludsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const reserva: IReserva = { id: 456 };
      const paciente: IPaciente = { id: 1952 };
      reserva.paciente = paciente;
      const medico: IMedico = { id: 29085 };
      reserva.medico = medico;
      const centroSalud: ICentroSalud = { id: 20294 };
      reserva.centroSalud = centroSalud;

      activatedRoute.data = of({ reserva });
      comp.ngOnInit();

      expect(comp.pacientesSharedCollection).toContain(paciente);
      expect(comp.medicosSharedCollection).toContain(medico);
      expect(comp.centroSaludsSharedCollection).toContain(centroSalud);
      expect(comp.reserva).toEqual(reserva);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReserva>>();
      const reserva = { id: 123 };
      jest.spyOn(reservaFormService, 'getReserva').mockReturnValue(reserva);
      jest.spyOn(reservaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reserva });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reserva }));
      saveSubject.complete();

      // THEN
      expect(reservaFormService.getReserva).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(reservaService.update).toHaveBeenCalledWith(expect.objectContaining(reserva));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReserva>>();
      const reserva = { id: 123 };
      jest.spyOn(reservaFormService, 'getReserva').mockReturnValue({ id: null });
      jest.spyOn(reservaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reserva: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reserva }));
      saveSubject.complete();

      // THEN
      expect(reservaFormService.getReserva).toHaveBeenCalled();
      expect(reservaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReserva>>();
      const reserva = { id: 123 };
      jest.spyOn(reservaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reserva });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reservaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePaciente', () => {
      it('Should forward to pacienteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pacienteService, 'comparePaciente');
        comp.comparePaciente(entity, entity2);
        expect(pacienteService.comparePaciente).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMedico', () => {
      it('Should forward to medicoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(medicoService, 'compareMedico');
        comp.compareMedico(entity, entity2);
        expect(medicoService.compareMedico).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCentroSalud', () => {
      it('Should forward to centroSaludService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(centroSaludService, 'compareCentroSalud');
        comp.compareCentroSalud(entity, entity2);
        expect(centroSaludService.compareCentroSalud).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
