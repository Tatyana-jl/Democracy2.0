import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAdminUser, defaultValue } from 'app/shared/model/admin-user.model';

export const ACTION_TYPES = {
  FETCH_ADMINUSER_LIST: 'adminUser/FETCH_ADMINUSER_LIST',
  FETCH_ADMINUSER: 'adminUser/FETCH_ADMINUSER',
  CREATE_ADMINUSER: 'adminUser/CREATE_ADMINUSER',
  UPDATE_ADMINUSER: 'adminUser/UPDATE_ADMINUSER',
  DELETE_ADMINUSER: 'adminUser/DELETE_ADMINUSER',
  RESET: 'adminUser/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAdminUser>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type AdminUserState = Readonly<typeof initialState>;

// Reducer

export default (state: AdminUserState = initialState, action): AdminUserState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ADMINUSER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADMINUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ADMINUSER):
    case REQUEST(ACTION_TYPES.UPDATE_ADMINUSER):
    case REQUEST(ACTION_TYPES.DELETE_ADMINUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ADMINUSER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADMINUSER):
    case FAILURE(ACTION_TYPES.CREATE_ADMINUSER):
    case FAILURE(ACTION_TYPES.UPDATE_ADMINUSER):
    case FAILURE(ACTION_TYPES.DELETE_ADMINUSER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADMINUSER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADMINUSER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADMINUSER):
    case SUCCESS(ACTION_TYPES.UPDATE_ADMINUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADMINUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/admin-users';

// Actions

export const getEntities: ICrudGetAllAction<IAdminUser> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ADMINUSER_LIST,
    payload: axios.get<IAdminUser>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IAdminUser> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADMINUSER,
    payload: axios.get<IAdminUser>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAdminUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADMINUSER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdminUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADMINUSER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdminUser> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADMINUSER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
