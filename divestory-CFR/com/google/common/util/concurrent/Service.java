/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.errorprone.annotations.DoNotMock;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@DoNotMock(value="Create an AbstractIdleService")
public interface Service {
    public void addListener(Listener var1, Executor var2);

    public void awaitRunning();

    public void awaitRunning(long var1, TimeUnit var3) throws TimeoutException;

    public void awaitTerminated();

    public void awaitTerminated(long var1, TimeUnit var3) throws TimeoutException;

    public Throwable failureCause();

    public boolean isRunning();

    public Service startAsync();

    public State state();

    public Service stopAsync();

    public static abstract class Listener {
        public void failed(State state, Throwable throwable) {
        }

        public void running() {
        }

        public void starting() {
        }

        public void stopping(State state) {
        }

        public void terminated(State state) {
        }
    }

    public static abstract class State
    extends Enum<State> {
        private static final /* synthetic */ State[] $VALUES;
        public static final /* enum */ State FAILED;
        public static final /* enum */ State NEW;
        public static final /* enum */ State RUNNING;
        public static final /* enum */ State STARTING;
        public static final /* enum */ State STOPPING;
        public static final /* enum */ State TERMINATED;

        static {
            State state;
            NEW = new State(){

                @Override
                boolean isTerminal() {
                    return false;
                }
            };
            STARTING = new State(){

                @Override
                boolean isTerminal() {
                    return false;
                }
            };
            RUNNING = new State(){

                @Override
                boolean isTerminal() {
                    return false;
                }
            };
            STOPPING = new State(){

                @Override
                boolean isTerminal() {
                    return false;
                }
            };
            TERMINATED = new State(){

                @Override
                boolean isTerminal() {
                    return true;
                }
            };
            FAILED = state = new State(){

                @Override
                boolean isTerminal() {
                    return true;
                }
            };
            $VALUES = new State[]{NEW, STARTING, RUNNING, STOPPING, TERMINATED, state};
        }

        public static State valueOf(String string2) {
            return Enum.valueOf(State.class, string2);
        }

        public static State[] values() {
            return (State[])$VALUES.clone();
        }

        abstract boolean isTerminal();

    }

}

