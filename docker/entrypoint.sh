#!/bin/sh

if [ ! -z "$JWT_ISSUER_URI_REDIRECT_HOST" ] && [ ! -z "$JWT_ISSUER_URI_REDIRECT_PORT" ]; then
  WAIT=1
  while [ $WAIT != 0 ]; do
    WAIT=0
    ping -c 1 $JWT_ISSUER_URI_REDIRECT_HOST 2>&1 >/dev/null || WAIT=1
    echo "JWT redirect host $JWT_ISSUER_URI_REDIRECT_HOST not up yet. Will retry in 5 seconds..."
    sleep 5
  done

  echo "Redirecting 127.0.0.1:$JWT_ISSUER_URI_REDIRECT_PORT to $JWT_ISSUER_URI_REDIRECT_HOST:$JWT_ISSUER_URI_REDIRECT_PORT"
  redir --lport=$JWT_ISSUER_URI_REDIRECT_PORT --laddr=127.0.0.1 --cport=$JWT_ISSUER_URI_REDIRECT_PORT --caddr=$JWT_ISSUER_URI_REDIRECT_HOST &
fi

if [ ! -z "$SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI" ] || [ ! -z "$IO_REMEDYMATCH_BACKEND_ENGINE_URL" ]; then
  WAIT=1
  while [ $WAIT != 0 ]; do
    WAIT=0

    # check if keycloak is up
    if [ ! -z "$SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI" ]; then
      curl -f -s "$SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI" 2>&1 >/dev/null || WAIT=1
    fi

    # check if camunda is up
    if [ ! -z "$IO_REMEDYMATCH_BACKEND_ENGINE_URL" ]; then
      curl -f -s "$IO_REMEDYMATCH_BACKEND_ENGINE_URL" 2>&1 >/dev/null || WAIT=1
    fi

    echo "Keycloak and Camunda are not ready yet. Will retry in 5 seconds..."
    sleep 5
  done
fi


exec "$@"
