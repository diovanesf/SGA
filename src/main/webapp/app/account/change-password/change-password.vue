<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-8 toastify-container">
        <h2 v-if="account" id="password-title">
          <span>
            Senha para [<strong>{{ username }}</strong
            >]</span
          >
        </h2>

        <div class="alert alert-success" role="alert" v-if="success">
          <strong>Senha alterada!</strong>
        </div>
        <div class="alert alert-danger" role="alert" v-if="error">
          <strong>Um erro ocorreu!</strong> A senha não pode ser alterada.
        </div>

        <div class="alert alert-danger" role="alert" v-if="doNotMatch">As senhas não coincidem!</div>

        <form name="form" role="form" id="password-form" v-on:submit.prevent="changePassword()">
          <div class="form-group">
            <label class="form-control-label" for="currentPassword">Senha atual</label>
            <input
              type="password"
              class="form-control"
              id="currentPassword"
              name="currentPassword"
              :class="{ valid: !$v.resetPassword.currentPassword.$invalid, invalid: $v.resetPassword.currentPassword.$invalid }"
              v-model="$v.resetPassword.currentPassword.$model"
              required
              data-cy="currentPassword"
            />
            <div v-if="$v.resetPassword.currentPassword.$anyDirty && $v.resetPassword.currentPassword.$invalid">
              <small class="form-text text-danger" v-if="!$v.resetPassword.currentPassword.required"> Sua senha é necessária. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="newPassword">Nova senha</label>
            <input
              type="password"
              class="form-control"
              id="newPassword"
              name="newPassword"
              :class="{ valid: !$v.resetPassword.newPassword.$invalid, invalid: $v.resetPassword.newPassword.$invalid }"
              v-model="$v.resetPassword.newPassword.$model"
              minlength="4"
              maxlength="50"
              required
              data-cy="newPassword"
            />
            <div v-if="$v.resetPassword.newPassword.$anyDirty && $v.resetPassword.newPassword.$invalid">
              <small class="form-text text-danger" v-if="!$v.resetPassword.newPassword.required"> Sua senha é necessária. </small>
              <small class="form-text text-danger" v-if="!$v.resetPassword.newPassword.minLength">
                Sua senha deve conter ao menos 4 caracteres.
              </small>
              <small class="form-text text-danger" v-if="!$v.resetPassword.newPassword.maxLength">
                Sua senha não deve ultrapassar 50 caracteres.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="confirmPassword">Confirmação da senha nova</label>
            <input
              type="password"
              class="form-control"
              id="confirmPassword"
              name="confirmPassword"
              :class="{ valid: !$v.resetPassword.confirmPassword.$invalid, invalid: $v.resetPassword.confirmPassword.$invalid }"
              v-model="$v.resetPassword.confirmPassword.$model"
              minlength="4"
              maxlength="50"
              required
              data-cy="confirmPassword"
            />
            <div v-if="$v.resetPassword.confirmPassword.$anyDirty && $v.resetPassword.confirmPassword.$invalid">
              <small class="form-text text-danger" v-if="!$v.resetAccount.confirmPassword.sameAsPassword">
                A senha e a confirmação não coincidem!
              </small>
            </div>
          </div>

          <button type="submit" :disabled="$v.resetPassword.$invalid" class="btn btn-success" data-cy="submit">Salvar</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./change-password.component.ts"></script>
