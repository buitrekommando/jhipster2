import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CentroSaludService } from '../service/centro-salud.service';
import { ICentroSalud } from '../centro-salud.model';
import { CentroSaludFormService } from './centro-salud-form.service';

import { CentroSaludUpdateComponent } from './centro-salud-update.component';

describe('CentroSalud Management Update Component', () => {
  let comp: CentroSaludUpdateComponent;
  let fixture: ComponentFixture<CentroSaludUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let centroSaludFormService: CentroSaludFormService;
  let centroSaludService: CentroSaludService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CentroSaludUpdateComponent],
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
      .overrideTemplate(CentroSaludUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CentroSaludUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    centroSaludFormService = TestBed.inject(CentroSaludFormService);
    centroSaludService = TestBed.inject(CentroSaludService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const centroSalud: ICentroSalud = { id: 456 };

      activatedRoute.data = of({ centroSalud });
      comp.ngOnInit();

      expect(comp.centroSalud).toEqual(centroSalud);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroSalud>>();
      const centroSalud = { id: 123 };
      jest.spyOn(centroSaludFormService, 'getCentroSalud').mockReturnValue(centroSalud);
      jest.spyOn(centroSaludService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroSalud });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centroSalud }));
      saveSubject.complete();

      // THEN
      expect(centroSaludFormService.getCentroSalud).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(centroSaludService.update).toHaveBeenCalledWith(expect.objectContaining(centroSalud));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroSalud>>();
      const centroSalud = { id: 123 };
      jest.spyOn(centroSaludFormService, 'getCentroSalud').mockReturnValue({ id: null });
      jest.spyOn(centroSaludService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroSalud: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centroSalud }));
      saveSubject.complete();

      // THEN
      expect(centroSaludFormService.getCentroSalud).toHaveBeenCalled();
      expect(centroSaludService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroSalud>>();
      const centroSalud = { id: 123 };
      jest.spyOn(centroSaludService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroSalud });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(centroSaludService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
