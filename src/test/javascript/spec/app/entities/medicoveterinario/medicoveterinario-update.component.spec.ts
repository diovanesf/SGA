/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import MedicoveterinarioUpdateComponent from '@/entities/medicoveterinario/medicoveterinario-update.vue';
import MedicoveterinarioClass from '@/entities/medicoveterinario/medicoveterinario-update.component';
import MedicoveterinarioService from '@/entities/medicoveterinario/medicoveterinario.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Medicoveterinario Management Update Component', () => {
    let wrapper: Wrapper<MedicoveterinarioClass>;
    let comp: MedicoveterinarioClass;
    let medicoveterinarioServiceStub: SinonStubbedInstance<MedicoveterinarioService>;

    beforeEach(() => {
      medicoveterinarioServiceStub = sinon.createStubInstance<MedicoveterinarioService>(MedicoveterinarioService);

      wrapper = shallowMount<MedicoveterinarioClass>(MedicoveterinarioUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          medicoveterinarioService: () => medicoveterinarioServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.medicoveterinario = entity;
        medicoveterinarioServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(medicoveterinarioServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.medicoveterinario = entity;
        medicoveterinarioServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(medicoveterinarioServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMedicoveterinario = { id: 123 };
        medicoveterinarioServiceStub.find.resolves(foundMedicoveterinario);
        medicoveterinarioServiceStub.retrieve.resolves([foundMedicoveterinario]);

        // WHEN
        comp.beforeRouteEnter({ params: { medicoveterinarioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.medicoveterinario).toBe(foundMedicoveterinario);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
