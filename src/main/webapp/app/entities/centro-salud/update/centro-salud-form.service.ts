import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICentroSalud, NewCentroSalud } from '../centro-salud.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICentroSalud for edit and NewCentroSaludFormGroupInput for create.
 */
type CentroSaludFormGroupInput = ICentroSalud | PartialWithRequiredKeyOf<NewCentroSalud>;

type CentroSaludFormDefaults = Pick<NewCentroSalud, 'id'>;

type CentroSaludFormGroupContent = {
  id: FormControl<ICentroSalud['id'] | NewCentroSalud['id']>;
  name: FormControl<ICentroSalud['name']>;
  address: FormControl<ICentroSalud['address']>;
};

export type CentroSaludFormGroup = FormGroup<CentroSaludFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CentroSaludFormService {
  createCentroSaludFormGroup(centroSalud: CentroSaludFormGroupInput = { id: null }): CentroSaludFormGroup {
    const centroSaludRawValue = {
      ...this.getFormDefaults(),
      ...centroSalud,
    };
    return new FormGroup<CentroSaludFormGroupContent>({
      id: new FormControl(
        { value: centroSaludRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(centroSaludRawValue.name, {
        validators: [Validators.required],
      }),
      address: new FormControl(centroSaludRawValue.address),
    });
  }

  getCentroSalud(form: CentroSaludFormGroup): ICentroSalud | NewCentroSalud {
    return form.getRawValue() as ICentroSalud | NewCentroSalud;
  }

  resetForm(form: CentroSaludFormGroup, centroSalud: CentroSaludFormGroupInput): void {
    const centroSaludRawValue = { ...this.getFormDefaults(), ...centroSalud };
    form.reset(
      {
        ...centroSaludRawValue,
        id: { value: centroSaludRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CentroSaludFormDefaults {
    return {
      id: null,
    };
  }
}
