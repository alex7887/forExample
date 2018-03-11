package com.ibank.service.command;

public class CommandFactoryHelper {
        /**
         * The suffix of name for commands.
         */
        public static final String COMMAND_SUFFIX = "Command";

        /**
         * The Spring's context Id.
         */
        public static final String ID = "commandFactoryHelper";

        /**
         * The logger.
         */
        private static final Log LOG = LogFactory.getLog(CommandFactoryHelper.class);

        /**
         * The message source.
         */
        private GateWayMessageSource messageSource;

        /**
         * The storage for command factories.
         */
        private Map<SystemType, AbstractCommandFactory> factories = new HashMap<SystemType, AbstractCommandFactory>(3);

        /**
         * @return the messageSource
         */
        public GateWayMessageSource getMessageSource() {
            return messageSource;
        }

        /**
         * @param messageSource the messageSource to set
         */
        @Required
        public void setMessageSource(GateWayMessageSource messageSource) {
            this.messageSource = messageSource;
        }

        /**
         * @return the factories
         */
        public Map<SystemType, AbstractCommandFactory> getFactories() {
            return factories;
        }

        /**
         * @param factories the factories to set
         */
        @Required
        public void setFactories(Map<SystemType, AbstractCommandFactory> factories) {
            this.factories = factories;
        }

        /**
         * Creates command for this element.
         * @param element request to handle.
         * @return the command for execution.
         * @throws GateWayException internal error for sending back.
         */
        public Command createCommand(JAXBElement<MessageType> element) throws GateWayException {
            // get type of message
            MessageType messageType = element.getValue();
            SystemType systemType = messageType.getSystem();

            // get factory
            AbstractCommandFactory commandFactory = factories.get(systemType);
            if(commandFactory != null) {
                LOG.info("creating command by : " + commandFactory);
                return commandFactory.createCommand(messageType);
            }
            else {
                String msg =messageSource.getMessage(GateWayMessageSource.UNKNOWN_SYSTEM_VALUE)
                        + " SYSTEM: " + systemType);
                LOG.error(msg);
                throw new GateWayException(msg);
            }
        }
}
