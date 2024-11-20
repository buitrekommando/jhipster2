import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CentroSaludDetailComponent } from './centro-salud-detail.component';

describe('CentroSalud Management Detail Component', () => {
  let comp: CentroSaludDetailComponent;
  let fixture: ComponentFixture<CentroSaludDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CentroSaludDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./centro-salud-detail.component').then(m => m.CentroSaludDetailComponent),
              resolve: { centroSalud: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CentroSaludDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CentroSaludDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load centroSalud on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CentroSaludDetailComponent);

      // THEN
      expect(instance.centroSalud()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
