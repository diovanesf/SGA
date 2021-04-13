/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ExameUpdateComponent from '@/entities/exame/exame-update.vue';
import ExameClass from '@/entities/exame/exame-update.component';
import ExameService from '@/entities/exame/exame.service';

import AmostraService from '@/entities/amostra/amostra.service';

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
  describe('Exame Management Update Component', () => {
    let wrapper: Wrapper<ExameClass>;
    let comp: ExameClass;
    let exameServiceStub: SinonStubbedInstance<ExameService>;

    beforeEach(() => {
      exameServiceStub = sinon.createStubInstance<ExameService>(ExameService);

      wrapper = shallowMount<ExameClass>(ExameUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          exameService: () => exameServiceStub,

          amostraService: () => new AmostraService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.exame = entity;
        exameServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(exameServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.exame = entity;
        exameServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(exameServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundExame = { id: 123 };
        exameServiceStub.find.resolves(foundExame);
        exameServiceStub.retrieve.resolves([foundExame]);

        // WHEN
        comp.beforeRouteEnter({ params: { exameId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.exame).toBe(foundExame);
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
