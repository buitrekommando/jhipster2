import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IMedico } from 'app/entities/medico/medico.model';
import { MedicoService } from 'app/entities/medico/service/medico.service';
import { ICentroSalud } from 'app/entities/centro-salud/centro-salud.model';
import { CentroSaludService } from 'app/entities/centro-salud/service/centro-salud.service';
import { IPaciente } from '../paciente.model';
import { PacienteService } from '../service/paciente.service';
import { PacienteFormService } from './paciente-form.service';

import { PacienteUpdateComponent } from './paciente-update.component';

describe('Paciente Management Update Component', () => {
  let comp: PacienteUpdateComponent;
  let fixture: ComponentFixture<PacienteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pacienteFormService: PacienteFormService;
  let pacienteService: PacienteService;
  let medicoService: MedicoService;
  let centroSaludService: CentroSaludService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PacienteUpdateComponent],
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
      .overrideTemplate(PacienteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PacienteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pacienteFormService = TestBed.inject(PacienteFormService);
    pacienteService = TestBed.inject(PacienteService);
    medicoService = TestBed.inject(MedicoService);
    centroSaludService = TestBed.inject(CentroSaludService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Medico query and add missing value', () => {
      const paciente: IPaciente = { id: 456 };
      const medico: IMedico = { id: 727 };
      paciente.medico = medico;

      const medicoCollection: IMedico[] = [{ id: 17887 }];
      jest.spyOn(medicoService, 'query').mockReturnValue(of(new HttpResponse({ body: medicoCollection })));
      const additionalMedicos = [medico];
      const expectedCollection: IMedico[] = [...additionalMedicos, ...medicoCollection];
      jest.spyOn(medicoService, 'addMedicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paciente });
      comp.ngOnInit();

      expect(medicoService.query).toHaveBeenCalled();
      expect(medicoService.addMedicoToCollectionIfMissing).toHaveBeenCalledWith(
        medicoCollection,
        ...additionalMedicos.map(expect.objectContaining),
      );
      expect(comp.medicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CentroSalud query and add missing value', () => {
      const paciente: IPaciente = { id: 456 };
      const centroSalud: ICentroSalud = { id: 8634 };
      paciente.centroSalud = centroSalud;

      const centroSaludCollection: ICentroSalud[] = [{ id: 27591 }];
      jest.spyOn(centroSaludService, 'query').mockReturnValue(of(new HttpResponse({ body: centroSaludCollection })));
      const additionalCentroSaluds = [centroSalud];
      const expectedCollection: ICentroSalud[] = [...additionalCentroSaluds, ...centroSaludCollection];
      jest.spyOn(centroSaludService, 'addCentroSaludToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paciente });
      comp.ngOnInit();

      expect(centroSaludService.query).toHaveBeenCalled();
      expect(centroSaludService.addCentroSaludToCollectionIfMissing).toHaveBeenCalledWith(
        centroSaludCollection,
        ...additionalCentroSaluds.map(expect.objectContaining),
      );
      expect(comp.centroSaludsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paciente: IPaciente = { id: 456 };
      const medico: IMedico = { id: 4237 };
      paciente.medico = medico;
      const centroSalud: ICentroSalud = { id: 10715 };
      paciente.centroSalud = centroSalud;

      activatedRoute.data = of({ paciente });
      comp.ngOnInit();

      expect(comp.medicosSharedCollection).toContain(medico);
      expect(comp.centroSaludsSharedCollection).toContain(centroSalud);
      expect(comp.paciente).toEqual(paciente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaciente>>();
      const paciente = { id: 123 };
      jest.spyOn(pacienteFormService, 'getPaciente').mockReturnValue(paciente);
      jest.spyOn(pacienteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paciente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paciente }));
      saveSubject.complete();

      // THEN
      expect(pacienteFormService.getPaciente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pacienteService.update).toHaveBeenCalledWith(expect.objectContaining(paciente));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaciente>>();
      const paciente = { id: 123 };
      jest.spyOn(pacienteFormService, 'getPaciente').mockReturnValue({ id: null });
      jest.spyOn(pacienteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paciente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paciente }));
      saveSubject.complete();

      // THEN
      expect(pacienteFormService.getPaciente).toHaveBeenCalled();
      expect(pacienteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaciente>>();
      const paciente = { id: 123 };
      jest.spyOn(pacienteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paciente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pacienteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
