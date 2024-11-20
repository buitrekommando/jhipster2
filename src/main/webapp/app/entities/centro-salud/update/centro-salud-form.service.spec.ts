import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../centro-salud.test-samples';

import { CentroSaludFormService } from './centro-salud-form.service';

describe('CentroSalud Form Service', () => {
  let service: CentroSaludFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CentroSaludFormService);
  });

  describe('Service methods', () => {
    describe('createCentroSaludFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCentroSaludFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            address: expect.any(Object),
          }),
        );
      });

      it('passing ICentroSalud should create a new form with FormGroup', () => {
        const formGroup = service.createCentroSaludFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            address: expect.any(Object),
          }),
        );
      });
    });

    describe('getCentroSalud', () => {
      it('should return NewCentroSalud for default CentroSalud initial value', () => {
        const formGroup = service.createCentroSaludFormGroup(sampleWithNewData);

        const centroSalud = service.getCentroSalud(formGroup) as any;

        expect(centroSalud).toMatchObject(sampleWithNewData);
      });

      it('should return NewCentroSalud for empty CentroSalud initial value', () => {
        const formGroup = service.createCentroSaludFormGroup();

        const centroSalud = service.getCentroSalud(formGroup) as any;

        expect(centroSalud).toMatchObject({});
      });

      it('should return ICentroSalud', () => {
        const formGroup = service.createCentroSaludFormGroup(sampleWithRequiredData);

        const centroSalud = service.getCentroSalud(formGroup) as any;

        expect(centroSalud).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICentroSalud should not enable id FormControl', () => {
        const formGroup = service.createCentroSaludFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCentroSalud should disable id FormControl', () => {
        const formGroup = service.createCentroSaludFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
