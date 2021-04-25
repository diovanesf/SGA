<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-8 toastify-container">
        <h1 id="register-title" data-cy="registerTitle">Cadastro</h1>

        <div class="alert alert-success" role="alert" v-if="success">
          <strong>Cadastro salvo!</strong> Confira seu e-mail para confirmação.
        </div>

        <div class="alert alert-danger" role="alert" v-if="error"><strong>Falha no cadastro!</strong> Tente novamente mais tarde.</div>

        <div class="alert alert-danger" role="alert" v-if="errorUserExists">
          <strong>Nome de usuário ja cadastrado!</strong> Por favor, escolha outro.
        </div>

        <div class="alert alert-danger" role="alert" v-if="errorEmailExists">
          <strong>Este e-mail ja está em uso!</strong> Por favor, escolha outro.
        </div>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col-md-8">
        <form id="register-form" name="registerForm" role="form" v-on:submit.prevent="register()" v-if="!success" no-validate>
          <div class="form-group">
            <label class="form-control-label" for="username">Usuário</label>
            <input
              type="text"
              class="form-control"
              v-model="$v.registerAccount.login.$model"
              id="username"
              name="login"
              :class="{ valid: !$v.registerAccount.login.$invalid, invalid: $v.registerAccount.login.$invalid }"
              required
              minlength="1"
              maxlength="50"
              pattern="^[a-zA-Z0-9!#$&'*+=?^_`{|}~.-]+@?[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$"
              data-cy="username"
            />
            <div v-if="$v.registerAccount.login.$anyDirty && $v.registerAccount.login.$invalid">
              <small class="form-text text-danger" v-if="!$v.registerAccount.login.required"> Seu nome de usuário é necessário. </small>
              <small class="form-text text-danger" v-if="!$v.registerAccount.login.minLength">
                Seu nome de usuário deve conter ao menos 1 caracter.
              </small>
              <small class="form-text text-danger" v-if="!$v.registerAccount.login.maxLength">
                Seu nome de usuário não deve ultrapassar 50 caracteres.
              </small>
              <small class="form-text text-danger" v-if="!$v.registerAccount.login.pattern">
                Seu nome de usuário deve conter somente letras e números.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="email">E-mail</label>
            <input
              type="email"
              class="form-control"
              id="email"
              name="email"
              :class="{ valid: !$v.registerAccount.email.$invalid, invalid: $v.registerAccount.email.$invalid }"
              v-model="$v.registerAccount.email.$model"
              minlength="5"
              maxlength="254"
              email
              required
              data-cy="email"
            />
            <div v-if="$v.registerAccount.email.$anyDirty && $v.registerAccount.email.$invalid">
              <small class="form-text text-danger" v-if="!$v.registerAccount.email.required"> Seu e-mail é necessário. </small>
              <small class="form-text text-danger" v-if="!$v.registerAccount.email.email"> Seu e-mail é invalido. </small>
              <small class="form-text text-danger" v-if="!$v.registerAccount.email.minLength">
                É necessário que seu e-mail contenha ao menos 5 caracteres.
              </small>
              <small class="form-text text-danger" v-if="!$v.registerAccount.email.maxLength">
                Seu e-mail não pode ultrapassar 100 caracteres.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="firstPassword">Nova senha</label>
            <input
              type="password"
              class="form-control"
              id="firstPassword"
              name="password"
              :class="{ valid: !$v.registerAccount.password.$invalid, invalid: $v.registerAccount.password.$invalid }"
              v-model="$v.registerAccount.password.$model"
              minlength="4"
              maxlength="50"
              required
              data-cy="firstPassword"
            />
            <div v-if="$v.registerAccount.password.$anyDirty && $v.registerAccount.password.$invalid">
              <small class="form-text text-danger" v-if="!$v.registerAccount.password.required"> Sua senha é necessária. </small>
              <small class="form-text text-danger" v-if="!$v.registerAccount.password.minLength">
                É necessário que sua senha tenha ao menos 4 caracteres.
              </small>
              <small class="form-text text-danger" v-if="!$v.registerAccount.password.maxLength">
                Sua senha não pode ultrapassar 50 caracteres.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="secondPassword">Confirmação da nova senha</label>
            <input
              type="password"
              class="form-control"
              id="secondPassword"
              name="confirmPasswordInput"
              :class="{ valid: !$v.confirmPassword.$invalid, invalid: $v.confirmPassword.$invalid }"
              v-model="$v.confirmPassword.$model"
              minlength="4"
              maxlength="50"
              required
              data-cy="secondPassword"
            />
            <div v-if="$v.confirmPassword.$dirty && $v.confirmPassword.$invalid">
              <small class="form-text text-danger" v-if="!$v.confirmPassword.required"> Sua confirmação de senha é necessária. </small>
              <small class="form-text text-danger" v-if="!$v.confirmPassword.minLength">
                É necessário que a confirmação da sua senha tenha ao menos 4 caracteres.
              </small>
              <small class="form-text text-danger" v-if="!$v.confirmPassword.maxLength">
                A confirmação da sua senha não pode ultrapassar 50 caracteres.
              </small>
              <small class="form-text text-danger" v-if="!$v.confirmPassword.sameAsPassword">
                A senha e a confirmação não coincidem!
              </small>
            </div>
          </div>

          <button type="submit" :disabled="$v.$invalid" class="btn btn-primary" data-cy="submit">Registrar</button>
        </form>
        <p></p>
        <div class="alert alert-warning">
          <span>Se quiser, faça </span>
          <a class="alert-link" v-on:click="openLogin()">log in</a
          ><span
            >, voce pode tentar as contas padrões:<br />- Administrador (login="admin" and password="admin") <br />- Usuário (login="user" and
            password="user").</span
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./register.component.ts"></script>
