import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProblem, defaultValue } from 'app/shared/model/problem.model';

export const ACTION_TYPES = {
  FETCH_PROBLEM_LIST: 'problem/FETCH_PROBLEM_LIST',
  FETCH_PROBLEM: 'problem/FETCH_PROBLEM',
  CREATE_PROBLEM: 'problem/CREATE_PROBLEM',
  UPDATE_PROBLEM: 'problem/UPDATE_PROBLEM',
  DELETE_PROBLEM: 'problem/DELETE_PROBLEM',
  SET_BLOB: 'problem/SET_BLOB',
  RESET: 'problem/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProblem>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ProblemState = Readonly<typeof initialState>;

// Reducer

export default (state: ProblemState = initialState, action): ProblemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROBLEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROBLEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PROBLEM):
    case REQUEST(ACTION_TYPES.UPDATE_PROBLEM):
    case REQUEST(ACTION_TYPES.DELETE_PROBLEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROBLEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROBLEM):
    case FAILURE(ACTION_TYPES.CREATE_PROBLEM):
    case FAILURE(ACTION_TYPES.UPDATE_PROBLEM):
    case FAILURE(ACTION_TYPES.DELETE_PROBLEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROBLEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROBLEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROBLEM):
    case SUCCESS(ACTION_TYPES.UPDATE_PROBLEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROBLEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/problems';

// Actions

export const getEntities: ICrudGetAllAction<IProblem> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PROBLEM_LIST,
    payload: axios.get<IProblem>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IProblem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROBLEM,
    payload: axios.get<IProblem>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IProblem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROBLEM,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProblem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROBLEM,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProblem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROBLEM,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
