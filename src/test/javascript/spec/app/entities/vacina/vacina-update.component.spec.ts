/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import VacinaUpdateComponent from '@/entities/vacina/vacina-update.vue';
import VacinaClass from '@/entities/vacina/vacina-update.component';
import VacinaService from '@/entities/vacina/vacina.service';

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
  describe('Vacina Management Update Component', () => {
    let wrapper: Wrapper<VacinaClass>;
    let comp: VacinaClass;
    let vacinaServiceStub: SinonStubbedInstance<VacinaService>;

    beforeEach(() => {
      vacinaServiceStub = sinon.createStubInstance<VacinaService>(VacinaService);

      wrapper = shallowMount<VacinaClass>(VacinaUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          vacinaService: () => vacinaServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.vacina = entity;
        vacinaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(vacinaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.vacina = entity;
        vacinaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(vacinaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVacina = { id: 123 };
        vacinaServiceStub.find.resolves(foundVacina);
        vacinaServiceStub.retrieve.resolves([foundVacina]);

        // WHEN
        comp.beforeRouteEnter({ params: { vacinaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.vacina).toBe(foundVacina);
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
