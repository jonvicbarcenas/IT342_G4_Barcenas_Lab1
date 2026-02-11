const DEFAULT_BASE_URL = 'http://localhost:8080';

export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || DEFAULT_BASE_URL;

export const AUTH_BASE_URL = `${API_BASE_URL}/api/auth`;

export const apiRequest = async (path, options = {}) => {
  const response = await fetch(path, {
    headers: {
      'Content-Type': 'application/json',
      ...(options.headers || {}),
    },
    ...options,
  });

  const data = await response.json().catch(() => null);
  return { response, data };
};
