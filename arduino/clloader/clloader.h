
/* 
 * {% copyright %}
 */

#include <stdio.h>
#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/select.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <termios.h>
#include <unistd.h>
 
/*#include "variant.h"               */                       /* Name of /dev/tty */


#define _XOPEN_SOURCE       /* See feature_test_macros(7) */
#define MAX_ARGS 0x20

#define CL_LOADER_CMD_HOST_START_DOWNLOAD_CMD "~sketch download"

#define CL_LOADER_CMD_STARTCHAR '#'
#define CL_LOADER_CMD_START_SKETCH_APP "#Start Sketch:" /* filename args and exec i/o wrapper */

extern int clLoaderFSM(int argc, char **argp);

typedef enum {
	FSM_STATE_PASSTHROUGH_INIT = 0,
	FSM_STATE_PASSTHROUGH_RUNNING,
	FSM_STATE_ZMODEM,
}fsm_t;

struct daemon_state {
	fsm_t fsm_state;            /* State of daemon */
	int tty_to_host;            /* handle to host port e.g. /dev/ttyGS0 */
	int tty_from_host;          /* handle from host port e.g. /dev/ttyGS0 */
	int tty_slave;              /* handle to slave port e.g. /dev/ptmx */
	char * slavename;           /* name of slave */
	pid_t slave_pid;               /* fork */
	char *sketch_args;
};

#define CLANTON_SKETCHPROGRAM "/sketch/sketch.elf"
